/*******************************************************************************
 * Copyright (c) 2000, 2008 QNX Software Systems and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     QNX Software Systems - Initial API and implementation
 *     Anton Leherbauer (Wind River Systems)
 *******************************************************************************/
package org.eclipse.cdt.internal.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICModelStatus;
import org.eclipse.cdt.core.model.ICModelStatusConstants;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/**
 * This operation copies/moves/renames a collection of resources from their current
 * container to a new container, optionally renaming the
 * elements.
 * <p>Notes:<ul>
 *    <li>If there is already an resource with the same name in
 *    the new container, the operation either overwrites or aborts,
 *    depending on the collision policy setting. The default setting is
 *    abort.
 *
 *    <li>The collection of elements being copied must all share the
 *    same type of container.
 *
 *    <li>This operation can be used to copy and rename elements within
 *    the same container.
 *
 *    <li>This operation only copies translation units.
 * </ul>
 *
 */
public class CopyResourceElementsOperation extends MultiOperation {

	/**
	 * The list of new resources created during this operation.
	 */
	protected ArrayList<ICElement> fCreatedElements;

	/**
	 * Table specifying deltas for elements being
	 * copied/moved/renamed. Keyed by elements' project(s), and
	 * values are the corresponding deltas.
	 */
	protected Map<ICProject, CElementDelta> fDeltasPerProject = new HashMap<ICProject, CElementDelta>(1);

	public CopyResourceElementsOperation(ICElement[] src, ICElement[] dst, boolean force) {
		super(src, dst, force);
	}

	/**
	 * Returns the <code>CElementDelta</code> for <code>cProject</code>,
	 * creating it and putting it in <code>fDeltasPerProject</code> if
	 * it does not exist yet.
	 */
	private CElementDelta getDeltaFor(ICProject cProject) {
		CElementDelta delta = fDeltasPerProject.get(cProject);
		if (delta == null) {
			delta = new CElementDelta(cProject);
			fDeltasPerProject.put(cProject, delta);
		}
		return delta;
	}

	/**
	 * @see MultiOperation
	 */
	@Override
	protected String getMainTaskName() {
		return CoreModelMessages.getString("operation.copyResourceProgress"); //$NON-NLS-1$
	}

	/**
	 * Sets the deltas to register the changes resulting from this operation
	 * for this source element and its destination.
	 * If the operation is a cross project operation<ul>
	 * <li>On a copy, the delta should be rooted in the dest project
	 * <li>On a move, two deltas are generated<ul>
	 *                      <li>one rooted in the source project
	 *                      <li>one rooted in the destination project</ul></ul>
	 * If the operation is rooted in a single project, the delta is rooted in that project
	 *
	 */
	protected void prepareDeltas(ICElement sourceElement, ICElement destinationElement) {
		ICProject destProject = destinationElement.getCProject();
		if (isMove()) {
			ICProject sourceProject = sourceElement.getCProject();
			getDeltaFor(sourceProject).movedFrom(sourceElement, destinationElement);
			getDeltaFor(destProject).movedTo(destinationElement, sourceElement);
		} else {
			getDeltaFor(destProject).added(destinationElement);
		}
	}

	/**
	 * Process all of the changed deltas generated by this operation.
	 */
	protected void processDeltas() {
		for (CElementDelta elementDelta : this.fDeltasPerProject.values()) {
			addDelta(elementDelta);
		}
	}

	/**
	 * Copies/moves a compilation unit with the name <code>newName</code>
	 * to the destination package.<br>
	 * The package statement in the compilation unit is updated if necessary.
	 * The main type of the compilation unit is renamed if necessary.
	 *
	 * @exception CModelException if the operation is unable to
	 * complete
	 */
	private void processResource(ICElement source, ICElement dest) throws CModelException {
		String newName = getNewNameFor(source);
		String destName = (newName != null) ? newName : source.getElementName();

		// copy resource
		IFile sourceResource = (IFile) source.getResource();
		// can be an IFolder or an IProject
		IContainer destFolder = (IContainer) dest.getResource();
		IFile destFile = destFolder.getFile(new Path(destName));
		if (!destFile.equals(sourceResource)) {
			try {
				if (destFile.exists()) {
					if (fForce) {
						// we can remove it
						deleteResource(destFile, false);
					} else {
						// abort
						throw new CModelException(new CModelStatus(ICModelStatusConstants.NAME_COLLISION));
					}
				}
				if (this.isMove()) {
					sourceResource.move(destFile.getFullPath(), fForce, true, getSubProgressMonitor(1));
				} else {
					sourceResource.copy(destFile.getFullPath(), fForce, getSubProgressMonitor(1));
				}
				this.hasModifiedResource = true;
			} catch (CModelException e) {
				throw e;
			} catch (CoreException e) {
				throw new CModelException(e);
			}

			// update new resource content

			// register the correct change deltas
			ICElement cdest = CModelManager.getDefault().create(destFile, null);
			prepareDeltas(source, cdest);
			fCreatedElements.add(cdest);
			//if (newName != null) {
			//the main type has been renamed
			//String oldName = source.getElementName();
			//oldName = oldName.substring(0, oldName.length() - 5);
			//String nName = newName;
			//nName = nName.substring(0, nName.length() - 5);
			//prepareDeltas(source.getType(oldName), cdest.getType(nName));
			//}
		} else {
			if (!fForce) {
				throw new CModelException(new CModelStatus(ICModelStatusConstants.NAME_COLLISION));
			}
			// update new resource content
			// in case we do a saveas on the same resource we have to simply update the contents
			// see http://dev.eclipse.org/bugs/show_bug.cgi?id=9351
		}
	}

	/**
	 * @see MultiOperation
	 * This method delegates to <code>processResource</code> or
	 * <code>processPackageFragmentResource</code>, depending on the type of
	 * <code>element</code>.
	 */
	@Override
	protected void processElement(ICElement element) throws CModelException {
		ICElement dest = getDestinationParent(element);
		if (element.getElementType() <= ICElement.C_UNIT) {
			processResource(element, dest);
			//fCreatedElements.add(dest.getCompilationUnit(element.getElementName()));
		} else {
			throw new CModelException(new CModelStatus(ICModelStatusConstants.INVALID_ELEMENT_TYPES, element));
		}
	}

	/**
	 * @see MultiOperation
	 * Overridden to allow special processing of <code>CElementDelta</code>s
	 * and <code>fResultElements</code>.
	 */
	@Override
	protected void processElements() throws CModelException {
		fCreatedElements = new ArrayList<ICElement>(fElementsToProcess.length);
		try {
			super.processElements();
		} catch (CModelException cme) {
			throw cme;
		} finally {
			fResultElements = new ICElement[fCreatedElements.size()];
			fCreatedElements.toArray(fResultElements);
			processDeltas();
		}
	}

	/**
	 * Possible failures:
	 * <ul>
	 *  <li>NO_ELEMENTS_TO_PROCESS - no elements supplied to the operation
	 *      <li>INDEX_OUT_OF_BOUNDS - the number of renamings supplied to the operation
	 *              does not match the number of elements that were supplied.
	 * </ul>
	 */
	@Override
	protected ICModelStatus verify() {
		ICModelStatus status = super.verify();
		if (!status.isOK()) {
			return status;
		}

		if (fRenamingsList != null && fRenamingsList.length != fElementsToProcess.length) {
			return new CModelStatus(ICModelStatusConstants.INDEX_OUT_OF_BOUNDS);
		}
		return CModelStatus.VERIFIED_OK;
	}

	/**
	 * @see MultiOperation
	 */
	@Override
	protected void verify(ICElement element) throws CModelException {
		if (element == null || !element.exists())
			error(ICModelStatusConstants.ELEMENT_DOES_NOT_EXIST, element);
		else if (element.isReadOnly() && (isRename() || isMove()))
			error(ICModelStatusConstants.READ_ONLY, element);
		else {
			CElement dest = (CElement) getDestinationParent(element);
			verifyDestination(element, dest);
			if (fRenamings != null) {
				verifyRenaming(element);
			}
		}
	}
}
