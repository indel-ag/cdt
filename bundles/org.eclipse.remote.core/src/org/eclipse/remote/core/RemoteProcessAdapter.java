/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.remote.core;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility class to adapt an IRemoteProcess to a java.lang.Process
 * 
 * @author crecoskie
 * 
 */
public class RemoteProcessAdapter extends Process {

	private final IRemoteProcess fProcess;

	public RemoteProcessAdapter(IRemoteProcess process) {
		fProcess = process;
	}

	@Override
	public void destroy() {
		fProcess.destroy();

	}

	@Override
	public int exitValue() {
		return fProcess.exitValue();
	}

	@Override
	public InputStream getErrorStream() {
		return fProcess.getErrorStream();
	}

	@Override
	public InputStream getInputStream() {
		return fProcess.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() {
		return fProcess.getOutputStream();
	}

	@Override
	public int waitFor() throws InterruptedException {
		return fProcess.waitFor();
	}

}
