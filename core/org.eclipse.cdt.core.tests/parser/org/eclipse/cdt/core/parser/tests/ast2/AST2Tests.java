/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM - Initial API and implementation
 *    Markus Schorn (Wind River Systems)
 *    Andrew Ferguson (Symbian)
 *******************************************************************************/
package org.eclipse.cdt.core.parser.tests.ast2;

import java.io.IOException;

import junit.framework.TestSuite;

import org.eclipse.cdt.core.dom.ast.ASTSignatureUtil;
import org.eclipse.cdt.core.dom.ast.ASTTypeUtil;
import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTImageLocation;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTProblemDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTProblemStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.core.dom.ast.IArrayType;
import org.eclipse.cdt.core.dom.ast.IBasicType;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.ICompositeType;
import org.eclipse.cdt.core.dom.ast.IEnumeration;
import org.eclipse.cdt.core.dom.ast.IEnumerator;
import org.eclipse.cdt.core.dom.ast.IField;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.core.dom.ast.IFunctionType;
import org.eclipse.cdt.core.dom.ast.ILabel;
import org.eclipse.cdt.core.dom.ast.IMacroBinding;
import org.eclipse.cdt.core.dom.ast.IParameter;
import org.eclipse.cdt.core.dom.ast.IPointerType;
import org.eclipse.cdt.core.dom.ast.IProblemBinding;
import org.eclipse.cdt.core.dom.ast.IQualifierType;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.ITypedef;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.c.ICASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignatedInitializer;
import org.eclipse.cdt.core.dom.ast.c.ICASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTFieldDesignator;
import org.eclipse.cdt.core.dom.ast.c.ICASTPointer;
import org.eclipse.cdt.core.dom.ast.c.ICASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICArrayType;
import org.eclipse.cdt.core.dom.ast.c.ICExternalBinding;
import org.eclipse.cdt.core.dom.ast.c.ICFunctionScope;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPNamespace;
import org.eclipse.cdt.core.parser.ParserLanguage;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CFunction;
import org.eclipse.cdt.internal.core.dom.parser.c.CVisitor;
import org.eclipse.cdt.internal.core.parser.ParserException;

/**
 * Test the new AST.
 * 
 * @author Doug Schaefer
 */
public class AST2Tests extends AST2BaseTest {
	
	public static TestSuite suite() {
		return suite(AST2Tests.class);
	}
	
	public AST2Tests() {
		super();
	}
	
	public AST2Tests(String name) {
		super(name);
	}
	
	protected IASTTranslationUnit parseAndCheckBindings( String code ) throws Exception {
		return parseAndCheckBindings(code, ParserLanguage.C);
	}
	
	public void testBug75189() throws Exception {
		parseAndCheckBindings( "struct A{};\n typedef int (*F) (struct A*);" ); //$NON-NLS-1$
		parseAndCheckBindings( "struct A{};\n typedef int (*F) (A*);", ParserLanguage.CPP ); //$NON-NLS-1$
	}
	
	public void testBug75340() throws Exception {
		IASTTranslationUnit tu = parseAndCheckBindings( "void f(int i = 0, int * p = 0);"); //$NON-NLS-1$
		IASTSimpleDeclaration sd = (IASTSimpleDeclaration) tu.getDeclarations()[0];
		assertEquals( ASTSignatureUtil.getParameterSignature( sd.getDeclarators()[0] ), "(int, int *)" ); //$NON-NLS-1$
	}
	
	// int *p1; int *p2;
	// union {
	// struct {int a; int b;} A;
	// struct {int a; int b;};
	// } MyStruct;
	// void test (void) {
	// p1 = &MyStruct.A.a;
	// p2 = &MyStruct.b;
	//         MyStruct.b = 1;
	// }
	public void testBug78103() throws Exception {
		parseAndCheckBindings( getAboveComment() );
	}
	
	// int m(int);
	// int (*pm)(int) = &m;
	// int f(int);
	// int x = f((*pm)(5));
	public void testBug43241() throws Exception {
		parseAndCheckBindings(getAboveComment());
	}
	
	// int *zzz1 (char);
	// int (*zzz2) (char); 
	// int ((*zzz3)) (char); 
	// int (*(zzz4)) (char); 
	public void testBug40768() throws Exception {
		IASTTranslationUnit tu = parse( getAboveComment(), ParserLanguage.C ); 
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		assertNoProblemBindings( col );
	}
	
	// int x;
	// void f(int y) {
	//    int z = x + y;
	// }
	public void testBasicFunction() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		IScope globalScope = tu.getScope();
		
		IASTDeclaration[] declarations = tu.getDeclarations();
		
		// int x
		IASTSimpleDeclaration decl_x = (IASTSimpleDeclaration) declarations[0];
		IASTSimpleDeclSpecifier declspec_x = (IASTSimpleDeclSpecifier) decl_x
		.getDeclSpecifier();
		assertEquals(IASTSimpleDeclSpecifier.t_int, declspec_x.getType());
		IASTDeclarator declor_x = decl_x.getDeclarators()[0];
		IASTName name_x = declor_x.getName();
		assertEquals("x", name_x.toString()); //$NON-NLS-1$
		
		// function - void f()
		IASTFunctionDefinition funcdef_f = (IASTFunctionDefinition) declarations[1];
		IASTSimpleDeclSpecifier declspec_f = (IASTSimpleDeclSpecifier) funcdef_f
		.getDeclSpecifier();
		assertEquals(IASTSimpleDeclSpecifier.t_void, declspec_f.getType());
		IASTFunctionDeclarator declor_f = funcdef_f.getDeclarator();
		IASTName name_f = declor_f.getName();
		assertEquals("f", name_f.toString()); //$NON-NLS-1$
		
		// parameter - int y
		assertTrue(declor_f instanceof IASTStandardFunctionDeclarator);
		IASTParameterDeclaration decl_y = ((IASTStandardFunctionDeclarator) declor_f)
		.getParameters()[0];
		IASTSimpleDeclSpecifier declspec_y = (IASTSimpleDeclSpecifier) decl_y
		.getDeclSpecifier();
		assertEquals(IASTSimpleDeclSpecifier.t_int, declspec_y.getType());
		IASTDeclarator declor_y = decl_y.getDeclarator();
		IASTName name_y = declor_y.getName();
		assertEquals("y", name_y.toString()); //$NON-NLS-1$
		
		// int z
		IASTCompoundStatement body_f = (IASTCompoundStatement) funcdef_f
		.getBody();
		IASTDeclarationStatement declstmt_z = (IASTDeclarationStatement) body_f
		.getStatements()[0];
		IASTSimpleDeclaration decl_z = (IASTSimpleDeclaration) declstmt_z
		.getDeclaration();
		IASTSimpleDeclSpecifier declspec_z = (IASTSimpleDeclSpecifier) decl_z
		.getDeclSpecifier();
		assertEquals(IASTSimpleDeclSpecifier.t_int, declspec_z.getType());
		IASTDeclarator declor_z = decl_z.getDeclarators()[0];
		IASTName name_z = declor_z.getName();
		assertEquals("z", name_z.toString()); //$NON-NLS-1$
		
		// = x + y
		IASTInitializerExpression initializer = (IASTInitializerExpression) declor_z
		.getInitializer();
		IASTBinaryExpression init_z = (IASTBinaryExpression) initializer
		.getExpression();
		assertEquals(IASTBinaryExpression.op_plus, init_z.getOperator());
		IASTIdExpression ref_x = (IASTIdExpression) init_z.getOperand1();
		IASTName name_ref_x = ref_x.getName();
		assertEquals("x", name_ref_x.toString()); //$NON-NLS-1$
		
		IASTIdExpression ref_y = (IASTIdExpression) init_z.getOperand2();
		IASTName name_ref_y = ref_y.getName();
		assertEquals("y", name_ref_y.toString()); //$NON-NLS-1$
		
		// BINDINGS
		// resolve the binding to get the variable object
		IVariable var_x = (IVariable) name_x.resolveBinding();
		assertEquals(globalScope, var_x.getScope());
		IFunction func_f = (IFunction) name_f.resolveBinding();
		assertEquals(globalScope, func_f.getScope());
		IParameter var_y = (IParameter) name_y.resolveBinding();
		assertEquals(((IASTCompoundStatement) funcdef_f.getBody()).getScope(),
				var_y.getScope());
		
		IVariable var_z = (IVariable) name_z.resolveBinding();
		assertEquals(((ICFunctionScope) func_f.getFunctionScope())
				.getBodyScope(), var_z.getScope());
		
		// make sure the variable referenced is the same one we declared above
		assertEquals(var_x, name_ref_x.resolveBinding());
		assertEquals(var_y, name_ref_y.resolveBinding());
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_x.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
		
		decls = tu.getDeclarationsInAST(name_f.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_f);
		
		decls = tu.getDeclarationsInAST(name_y.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_y);
		
		decls = tu.getDeclarationsInAST(name_z.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_z);
		
		decls = tu.getDeclarationsInAST(name_ref_x.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
		
		decls = tu.getDeclarationsInAST(name_ref_y.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_y);
		
		// // test clearBindings
		// assertNotNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("x").toCharArray()));
		// //$NON-NLS-1$
		// assertNotNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("f").toCharArray()));
		// //$NON-NLS-1$
		// assertNotNull(((ICScope) body_f.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("z").toCharArray()));
		// //$NON-NLS-1$
		// assertNotNull(((ICScope) body_f.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("y").toCharArray()));
		// //$NON-NLS-1$
		// CVisitor.clearBindings(tu);
		// assertNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("x").toCharArray()));
		// //$NON-NLS-1$
		// assertNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("f").toCharArray()));
		// //$NON-NLS-1$
		// assertNull(((ICScope) body_f.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("z").toCharArray()));
		// //$NON-NLS-1$
		// assertNull(((ICScope) body_f.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("y").toCharArray()));
		// //$NON-NLS-1$
	}
	
	// typedef struct {
	//     int x;
	// } S;
	//
	// void f() {
	//     S myS;
	//     myS.x = 5;
	// }
	public void testSimpleStruct() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTCompositeTypeSpecifier type = (IASTCompositeTypeSpecifier) decl
		.getDeclSpecifier();
		
		// it's a typedef
		assertEquals(IASTDeclSpecifier.sc_typedef, type.getStorageClass());
		// this an anonymous struct
		IASTName name_struct = type.getName();
		assertTrue(name_struct.isDeclaration());
		assertFalse(name_struct.isReference());
		assertEquals("", name_struct.toString()); //$NON-NLS-1$
		// member - x
		IASTSimpleDeclaration decl_x = (IASTSimpleDeclaration) type
		.getMembers()[0];
		IASTSimpleDeclSpecifier spec_x = (IASTSimpleDeclSpecifier) decl_x
		.getDeclSpecifier();
		// it's an int
		assertEquals(IASTSimpleDeclSpecifier.t_int, spec_x.getType());
		IASTDeclarator tor_x = decl_x.getDeclarators()[0];
		IASTName name_x = tor_x.getName();
		assertEquals("x", name_x.toString()); //$NON-NLS-1$
		
		// declarator S
		IASTDeclarator tor_S = decl.getDeclarators()[0];
		IASTName name_S = tor_S.getName();
		assertEquals("S", name_S.toString()); //$NON-NLS-1$
		
		// function f
		IASTFunctionDefinition def_f = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		// f's body
		IASTCompoundStatement body_f = (IASTCompoundStatement) def_f.getBody();
		// the declaration statement for myS
		IASTDeclarationStatement declstmt_myS = (IASTDeclarationStatement) body_f
		.getStatements()[0];
		// the declaration for myS
		IASTSimpleDeclaration decl_myS = (IASTSimpleDeclaration) declstmt_myS
		.getDeclaration();
		// the type specifier for myS
		IASTNamedTypeSpecifier type_spec_myS = (IASTNamedTypeSpecifier) decl_myS
		.getDeclSpecifier();
		// the type name for myS
		IASTName name_type_myS = type_spec_myS.getName();
		// the declarator for myS
		IASTDeclarator tor_myS = decl_myS.getDeclarators()[0];
		// the name for myS
		IASTName name_myS = tor_myS.getName();
		// the assignment expression statement
		IASTExpressionStatement exprstmt = (IASTExpressionStatement) body_f
		.getStatements()[1];
		// the assignment expression
		IASTBinaryExpression assexpr = (IASTBinaryExpression) exprstmt
		.getExpression();
		// the field reference to myS.x
		IASTFieldReference fieldref = (IASTFieldReference) assexpr
		.getOperand1();
		// the reference to myS
		IASTIdExpression ref_myS = (IASTIdExpression) fieldref.getFieldOwner();
		IASTLiteralExpression lit_5 = (IASTLiteralExpression) assexpr
		.getOperand2();
		assertEquals("5", lit_5.toString()); //$NON-NLS-1$
		
		// Logical Bindings In Test
		ICompositeType type_struct = (ICompositeType) name_struct
		.resolveBinding();
		ITypedef typedef_S = (ITypedef) name_S.resolveBinding();
		// make sure the typedef is hooked up correctly
		assertEquals(type_struct, typedef_S.getType());
		// the typedef S for myS
		ITypedef typedef_myS = (ITypedef) name_type_myS.resolveBinding();
		assertEquals(typedef_S, typedef_myS);
		// get the real type for S which is our anonymous struct
		ICompositeType type_myS = (ICompositeType) typedef_myS.getType();
		assertEquals(type_myS, type_struct);
		// the variable myS
		IVariable var_myS = (IVariable) name_myS.resolveBinding();
		assertEquals(typedef_S, var_myS.getType());
		assertEquals(var_myS, ref_myS.getName().resolveBinding());
		IField field_x = (IField) name_x.resolveBinding();
		assertEquals(field_x, fieldref.getFieldName().resolveBinding());
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_struct.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_struct);
		
		decls = tu.getDeclarationsInAST(name_x.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
		
		decls = tu.getDeclarationsInAST(def_f.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], def_f.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(name_S.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_S);
		
		decls = tu.getDeclarationsInAST(name_myS.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_myS);
		
		decls = tu.getDeclarationsInAST(ref_myS.getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_myS);
		
		decls = tu.getDeclarationsInAST(fieldref.getFieldName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
	}
	
	public void testCExpressions() throws ParserException {
		validateSimpleUnaryExpressionC("++x", IASTUnaryExpression.op_prefixIncr); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("--x", IASTUnaryExpression.op_prefixDecr); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("+x", IASTUnaryExpression.op_plus); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("-x", IASTUnaryExpression.op_minus); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("!x", IASTUnaryExpression.op_not); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("~x", IASTUnaryExpression.op_tilde); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("*x", IASTUnaryExpression.op_star); //$NON-NLS-1$
		validateSimpleUnaryExpressionC("&x", IASTUnaryExpression.op_amper); //$NON-NLS-1$
		validateSimpleUnaryExpressionC(
				"sizeof x", IASTUnaryExpression.op_sizeof); //$NON-NLS-1$
		validateSimpleTypeIdExpressionC(
				"sizeof( int )", IASTTypeIdExpression.op_sizeof); //$NON-NLS-1$
		validateSimpleUnaryTypeIdExpression(
				"(int)x", IASTCastExpression.op_cast); //$NON-NLS-1$
		validateSimplePostfixInitializerExpressionC("(int) { 5 }"); //$NON-NLS-1$
		validateSimplePostfixInitializerExpressionC("(int) { 5, }"); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x=y", IASTBinaryExpression.op_assign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x*=y", IASTBinaryExpression.op_multiplyAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x/=y", IASTBinaryExpression.op_divideAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x%=y", IASTBinaryExpression.op_moduloAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x+=y", IASTBinaryExpression.op_plusAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x-=y", IASTBinaryExpression.op_minusAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x<<=y", IASTBinaryExpression.op_shiftLeftAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x>>=y", IASTBinaryExpression.op_shiftRightAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x&=y", IASTBinaryExpression.op_binaryAndAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x^=y", IASTBinaryExpression.op_binaryXorAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x|=y", IASTBinaryExpression.op_binaryOrAssign); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x-y", IASTBinaryExpression.op_minus); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x+y", IASTBinaryExpression.op_plus); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x/y", IASTBinaryExpression.op_divide); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x*y", IASTBinaryExpression.op_multiply); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x%y", IASTBinaryExpression.op_modulo); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x<<y", IASTBinaryExpression.op_shiftLeft); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x>>y", IASTBinaryExpression.op_shiftRight); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x<y", IASTBinaryExpression.op_lessThan); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x>y", IASTBinaryExpression.op_greaterThan); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x<=y", IASTBinaryExpression.op_lessEqual); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x>=y", IASTBinaryExpression.op_greaterEqual); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x==y", IASTBinaryExpression.op_equals); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x!=y", IASTBinaryExpression.op_notequals); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x&y", IASTBinaryExpression.op_binaryAnd); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x^y", IASTBinaryExpression.op_binaryXor); //$NON-NLS-1$
		validateSimpleBinaryExpressionC("x|y", IASTBinaryExpression.op_binaryOr); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x&&y", IASTBinaryExpression.op_logicalAnd); //$NON-NLS-1$
		validateSimpleBinaryExpressionC(
				"x||y", IASTBinaryExpression.op_logicalOr); //$NON-NLS-1$
		validateConditionalExpressionC("x ? y : x"); //$NON-NLS-1$
	}
	
	public void testMultipleDeclarators() throws Exception {
		IASTTranslationUnit tu = parse("int r, s;", ParserLanguage.C); //$NON-NLS-1$
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTDeclarator[] declarators = decl.getDeclarators();
		assertEquals(2, declarators.length);
		
		IASTDeclarator dtor1 = declarators[0];
		IASTDeclarator dtor2 = declarators[1];
		
		IASTName name1 = dtor1.getName();
		IASTName name2 = dtor2.getName();
		
		assertEquals(name1.resolveBinding().getName(), "r"); //$NON-NLS-1$
		assertEquals(name2.resolveBinding().getName(), "s"); //$NON-NLS-1$
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name1);
		
		decls = tu.getDeclarationsInAST(name2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name2);
	}
	
	
	public void testStructureTagScoping_1() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("struct A;             \n"); //$NON-NLS-1$
		buffer.append("void f(){             \n"); //$NON-NLS-1$
		buffer.append("   struct A;          \n"); //$NON-NLS-1$
		buffer.append("   struct A * a;      \n"); //$NON-NLS-1$
		buffer.append("}                     \n"); //$NON-NLS-1$
		
		IASTTranslationUnit tu = parse(buffer.toString(), ParserLanguage.C);
		
		// struct A;
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTElaboratedTypeSpecifier compTypeSpec = (IASTElaboratedTypeSpecifier) decl1
		.getDeclSpecifier();
		assertEquals(0, decl1.getDeclarators().length);
		IASTName nameA1 = compTypeSpec.getName();
		
		// void f() {
		IASTFunctionDefinition fndef = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		IASTCompoundStatement compoundStatement = (IASTCompoundStatement) fndef
		.getBody();
		assertEquals(2, compoundStatement.getStatements().length);
		
		// struct A;
		IASTDeclarationStatement declStatement = (IASTDeclarationStatement) compoundStatement
		.getStatements()[0];
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) declStatement
		.getDeclaration();
		compTypeSpec = (IASTElaboratedTypeSpecifier) decl2.getDeclSpecifier();
		assertEquals(0, decl2.getDeclarators().length);
		IASTName nameA2 = compTypeSpec.getName();
		
		// struct A * a;
		declStatement = (IASTDeclarationStatement) compoundStatement
		.getStatements()[1];
		IASTSimpleDeclaration decl3 = (IASTSimpleDeclaration) declStatement
		.getDeclaration();
		compTypeSpec = (IASTElaboratedTypeSpecifier) decl3.getDeclSpecifier();
		IASTName nameA3 = compTypeSpec.getName();
		IASTDeclarator dtor = decl3.getDeclarators()[0];
		IASTName namea = dtor.getName();
		assertEquals(1, dtor.getPointerOperators().length);
		assertTrue(dtor.getPointerOperators()[0] instanceof ICASTPointer);
		
		// bindings
		ICompositeType str1 = (ICompositeType) nameA1.resolveBinding();
		ICompositeType str2 = (ICompositeType) nameA2.resolveBinding();
		IVariable var = (IVariable) namea.resolveBinding();
		IType str3pointer = var.getType();
		assertTrue(str3pointer instanceof IPointerType);
		ICompositeType str3 = (ICompositeType) ((IPointerType) str3pointer)
		.getType();
		ICompositeType str4 = (ICompositeType) nameA3.resolveBinding();
		assertNotNull(str1);
		assertNotNull(str2);
		assertNotSame(str1, str2);
		assertSame(str2, str3);
		assertSame(str3, str4);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(nameA1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], nameA1);
		
		decls = tu.getDeclarationsInAST(fndef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fndef.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(nameA2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], nameA2);
		
		decls = tu.getDeclarationsInAST(nameA3.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], nameA2);
		
		decls = tu.getDeclarationsInAST(namea.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], namea);
	}
	
	public void testStructureTagScoping_2() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("struct A;             \n"); //$NON-NLS-1$
		buffer.append("void f(){             \n"); //$NON-NLS-1$
		buffer.append("   struct A * a;      \n"); //$NON-NLS-1$
		buffer.append("}                     \r\n"); //$NON-NLS-1$
		
		IASTTranslationUnit tu = parse(buffer.toString(), ParserLanguage.C);
		
		// struct A;
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTElaboratedTypeSpecifier compTypeSpec = (IASTElaboratedTypeSpecifier) decl1
		.getDeclSpecifier();
		assertEquals(0, decl1.getDeclarators().length);
		IASTName nameA1 = compTypeSpec.getName();
		
		// void f() {
		IASTFunctionDefinition fndef = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		IASTCompoundStatement compoundStatement = (IASTCompoundStatement) fndef
		.getBody();
		assertEquals(1, compoundStatement.getStatements().length);
		
		// struct A * a;
		IASTDeclarationStatement declStatement = (IASTDeclarationStatement) compoundStatement
		.getStatements()[0];
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) declStatement
		.getDeclaration();
		compTypeSpec = (IASTElaboratedTypeSpecifier) decl2.getDeclSpecifier();
		IASTName nameA2 = compTypeSpec.getName();
		IASTDeclarator dtor = decl2.getDeclarators()[0];
		IASTName namea = dtor.getName();
		assertEquals(1, dtor.getPointerOperators().length);
		assertTrue(dtor.getPointerOperators()[0] instanceof ICASTPointer);
		
		// bindings
		ICompositeType str1 = (ICompositeType) nameA1.resolveBinding();
		ICompositeType str2 = (ICompositeType) nameA2.resolveBinding();
		IVariable var = (IVariable) namea.resolveBinding();
		IPointerType str3pointer = (IPointerType) var.getType();
		ICompositeType str3 = (ICompositeType) str3pointer.getType();
		assertNotNull(str1);
		assertSame(str1, str2);
		assertSame(str2, str3);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(nameA1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], nameA1);
		
		decls = tu.getDeclarationsInAST(fndef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fndef.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(nameA2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], nameA1);
		
		decls = tu.getDeclarationsInAST(namea.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], namea);
	}
	
	public void testStructureDef() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("struct A;                \r\n"); //$NON-NLS-1$
		buffer.append("struct A * a;            \n"); //$NON-NLS-1$
		buffer.append("struct A { int i; };     \n"); //$NON-NLS-1$
		buffer.append("void f() {               \n"); //$NON-NLS-1$
		buffer.append("   a->i;                 \n"); //$NON-NLS-1$
		buffer.append("}                        \n"); //$NON-NLS-1$
		
		IASTTranslationUnit tu = parse(buffer.toString(), ParserLanguage.C);
		
		// struct A;
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTElaboratedTypeSpecifier elabTypeSpec = (IASTElaboratedTypeSpecifier) decl1
		.getDeclSpecifier();
		assertEquals(0, decl1.getDeclarators().length);
		IASTName name_A1 = elabTypeSpec.getName();
		
		// struct A * a;
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) tu
		.getDeclarations()[1];
		elabTypeSpec = (IASTElaboratedTypeSpecifier) decl2.getDeclSpecifier();
		IASTName name_A2 = elabTypeSpec.getName();
		IASTDeclarator dtor = decl2.getDeclarators()[0];
		IASTName name_a = dtor.getName();
		assertEquals(1, dtor.getPointerOperators().length);
		assertTrue(dtor.getPointerOperators()[0] instanceof ICASTPointer);
		
		// struct A {
		IASTSimpleDeclaration decl3 = (IASTSimpleDeclaration) tu
		.getDeclarations()[2];
		ICASTCompositeTypeSpecifier compTypeSpec = (ICASTCompositeTypeSpecifier) decl3
		.getDeclSpecifier();
		IASTName name_Adef = compTypeSpec.getName();
		
		// int i;
		IASTSimpleDeclaration decl4 = (IASTSimpleDeclaration) compTypeSpec
		.getMembers()[0];
		dtor = decl4.getDeclarators()[0];
		IASTName name_i = dtor.getName();
		
		// void f() {
		IASTFunctionDefinition fndef = (IASTFunctionDefinition) tu
		.getDeclarations()[3];
		IASTCompoundStatement compoundStatement = (IASTCompoundStatement) fndef
		.getBody();
		assertEquals(1, compoundStatement.getStatements().length);
		
		// a->i;
		IASTExpressionStatement exprstmt = (IASTExpressionStatement) compoundStatement
		.getStatements()[0];
		IASTFieldReference fieldref = (IASTFieldReference) exprstmt
		.getExpression();
		IASTIdExpression id_a = (IASTIdExpression) fieldref.getFieldOwner();
		IASTName name_aref = id_a.getName();
		IASTName name_iref = fieldref.getFieldName();
		
		// bindings
		IVariable var_a1 = (IVariable) name_aref.resolveBinding();
		IVariable var_i1 = (IVariable) name_iref.resolveBinding();
		IPointerType structA_1pointer = (IPointerType) var_a1.getType();
		ICompositeType structA_1 = (ICompositeType) structA_1pointer.getType();
		ICompositeType structA_2 = (ICompositeType) name_A1.resolveBinding();
		ICompositeType structA_3 = (ICompositeType) name_A2.resolveBinding();
		ICompositeType structA_4 = (ICompositeType) name_Adef.resolveBinding();
		
		IVariable var_a2 = (IVariable) name_a.resolveBinding();
		IVariable var_i2 = (IVariable) name_i.resolveBinding();
		
		assertSame(var_a1, var_a2);
		assertSame(var_i1, var_i2);
		assertSame(structA_1, structA_2);
		assertSame(structA_2, structA_3);
		assertSame(structA_3, structA_4);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_A1.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_A1);
		assertEquals(decls[1], name_Adef);
		
		decls = tu.getDeclarationsInAST(name_A2.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_A1);
		assertEquals(decls[1], name_Adef);
		
		decls = tu.getDeclarationsInAST(name_a.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a);
		
		decls = tu.getDeclarationsInAST(name_Adef.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_A1);
		assertEquals(decls[1], name_Adef);
		
		decls = tu.getDeclarationsInAST(name_i.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
		
		decls = tu.getDeclarationsInAST(fndef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fndef.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(name_aref.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a);
		
		decls = tu.getDeclarationsInAST(name_iref.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
	}
	
	// struct x {};        
	// void f( int x ) {   
	//    struct x i;      
	// }  
	public void testStructureNamespace() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration declaration1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTCompositeTypeSpecifier typeSpec = (IASTCompositeTypeSpecifier) declaration1
		.getDeclSpecifier();
		IASTName x_1 = typeSpec.getName();
		
		IASTFunctionDefinition fdef = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		assertTrue(fdef.getDeclarator() instanceof IASTStandardFunctionDeclarator);
		IASTParameterDeclaration param = ((IASTStandardFunctionDeclarator) fdef
				.getDeclarator()).getParameters()[0];
		IASTName x_2 = param.getDeclarator().getName();
		
		IASTCompoundStatement compound = (IASTCompoundStatement) fdef.getBody();
		IASTDeclarationStatement declStatement = (IASTDeclarationStatement) compound
		.getStatements()[0];
		IASTSimpleDeclaration declaration2 = (IASTSimpleDeclaration) declStatement
		.getDeclaration();
		IASTElaboratedTypeSpecifier elab = (IASTElaboratedTypeSpecifier) declaration2
		.getDeclSpecifier();
		IASTName x_3 = elab.getName();
		
		ICompositeType x1 = (ICompositeType) x_1.resolveBinding();
		IVariable x2 = (IVariable) x_2.resolveBinding();
		ICompositeType x3 = (ICompositeType) x_3.resolveBinding();
		
		assertNotNull(x1);
		assertNotNull(x2);
		assertSame(x1, x3);
		assertNotSame(x2, x3);
		
		IASTDeclarator decl_i = declaration2.getDeclarators()[0];
		decl_i.getName().resolveBinding(); // add i's binding to the scope
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(x_1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], x_1);
		
		decls = tu.getDeclarationsInAST(fdef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fdef.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(x_2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], x_2);
		
		decls = tu.getDeclarationsInAST(x_3.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], x_1);
		
		decls = tu.getDeclarationsInAST(declaration2.getDeclarators()[0].getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], declaration2.getDeclarators()[0].getName());
		
		// assertNotNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_TAG, new String("x").toCharArray()));
		// //$NON-NLS-1$
		// assertNotNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("f").toCharArray()));
		// //$NON-NLS-1$
		// assertNotNull(((ICScope) compound.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("x").toCharArray()));
		// //$NON-NLS-1$
		// assertNotNull(((ICScope) compound.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("i").toCharArray()));
		// //$NON-NLS-1$
		// CVisitor.clearBindings(tu);
		// assertNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_TAG, new String("x").toCharArray()));
		// //$NON-NLS-1$
		// assertNull(((ICScope) tu.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("f").toCharArray()));
		// //$NON-NLS-1$
		// assertNull(((ICScope) compound.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("x").toCharArray()));
		// //$NON-NLS-1$
		// assertNull(((ICScope) compound.getScope()).getBinding(
		// ICScope.NAMESPACE_TYPE_OTHER, new String("i").toCharArray()));
		// //$NON-NLS-1$
	}
	
	// void f( int a );        
	// void f( int b ){        
	//    b;                   
	// }     
	public void testFunctionParameters() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		// void f(
		IASTSimpleDeclaration f_decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTStandardFunctionDeclarator dtor = (IASTStandardFunctionDeclarator) f_decl
		.getDeclarators()[0];
		IASTName f_name1 = dtor.getName();
		// int a );
		IASTParameterDeclaration param1 = dtor.getParameters()[0];
		IASTDeclarator paramDtor = param1.getDeclarator();
		IASTName name_param1 = paramDtor.getName();
		
		// void f(
		IASTFunctionDefinition f_defn = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		assertTrue(f_defn.getDeclarator() instanceof IASTStandardFunctionDeclarator);
		dtor = (IASTStandardFunctionDeclarator) f_defn.getDeclarator();
		IASTName f_name2 = dtor.getName();
		// int b );
		IASTParameterDeclaration param2 = dtor.getParameters()[0];
		paramDtor = param2.getDeclarator();
		IASTName name_param2 = paramDtor.getName();
		
		// b;
		IASTCompoundStatement compound = (IASTCompoundStatement) f_defn
		.getBody();
		IASTExpressionStatement expStatement = (IASTExpressionStatement) compound
		.getStatements()[0];
		IASTIdExpression idexp = (IASTIdExpression) expStatement
		.getExpression();
		IASTName name_param3 = idexp.getName();
		
		// bindings
		IParameter param_1 = (IParameter) name_param3.resolveBinding();
		IParameter param_2 = (IParameter) name_param2.resolveBinding();
		IParameter param_3 = (IParameter) name_param1.resolveBinding();
		IFunction f_1 = (IFunction) f_name1.resolveBinding();
		IFunction f_2 = (IFunction) f_name2.resolveBinding();
		
		assertNotNull(param_1);
		assertNotNull(f_1);
		assertSame(param_1, param_2);
		assertSame(param_2, param_3);
		assertSame(f_1, f_2);
		
		CVisitor.clearBindings(tu);
		param_1 = (IParameter) name_param1.resolveBinding();
		param_2 = (IParameter) name_param3.resolveBinding();
		param_3 = (IParameter) name_param2.resolveBinding();
		f_1 = (IFunction) f_name2.resolveBinding();
		f_2 = (IFunction) f_name1.resolveBinding();
		assertNotNull(param_1);
		assertNotNull(f_1);
		assertSame(param_1, param_2);
		assertSame(param_2, param_3);
		assertSame(f_1, f_2);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(f_name1.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], f_name1);
		assertEquals(decls[1], f_name2);
		
		decls = tu.getDeclarationsInAST(name_param1.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_param1);
		assertEquals(decls[1], name_param2);
		
		decls = tu.getDeclarationsInAST(f_name2.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], f_name1);
		assertEquals(decls[1], f_name2);
		
		decls = tu.getDeclarationsInAST(name_param2.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_param1);
		assertEquals(decls[1], name_param2);
	}
	
	// void f( int a, int b ) { }
	public void testSimpleFunction() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTFunctionDefinition fDef = (IASTFunctionDefinition) tu
		.getDeclarations()[0];
		assertTrue(fDef.getDeclarator() instanceof IASTStandardFunctionDeclarator);
		IASTStandardFunctionDeclarator fDtor = (IASTStandardFunctionDeclarator) fDef
		.getDeclarator();
		IASTName fName = fDtor.getName();
		
		IASTParameterDeclaration a = fDtor.getParameters()[0];
		IASTName name_a = a.getDeclarator().getName();
		
		IASTParameterDeclaration b = fDtor.getParameters()[1];
		IASTName name_b = b.getDeclarator().getName();
		
		IFunction function = (IFunction) fName.resolveBinding();
		IParameter param_a = (IParameter) name_a.resolveBinding();
		IParameter param_b = (IParameter) name_b.resolveBinding();
		
		assertEquals("f", function.getName()); //$NON-NLS-1$
		assertEquals("a", param_a.getName()); //$NON-NLS-1$
		assertEquals("b", param_b.getName()); //$NON-NLS-1$
		
		IParameter[] params = function.getParameters();
		assertEquals(2, params.length);
		assertSame(params[0], param_a);
		assertSame(params[1], param_b);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(fName.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fName);
		
		decls = tu.getDeclarationsInAST(name_a.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a);
		
		decls = tu.getDeclarationsInAST(name_b.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_b);
	}
	
	// void f();              
	// void g() {             
	//    f();                
	// }                      
	// void f(){ }   
	public void testSimpleFunctionCall() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		// void f();
		IASTSimpleDeclaration fdecl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTStandardFunctionDeclarator fdtor = (IASTStandardFunctionDeclarator) fdecl
		.getDeclarators()[0];
		IASTName name_f = fdtor.getName();
		
		// void g() {
		IASTFunctionDefinition gdef = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		
		// f();
		IASTCompoundStatement compound = (IASTCompoundStatement) gdef.getBody();
		IASTExpressionStatement expStatement = (IASTExpressionStatement) compound
		.getStatements()[0];
		IASTFunctionCallExpression fcall = (IASTFunctionCallExpression) expStatement
		.getExpression();
		IASTIdExpression fcall_id = (IASTIdExpression) fcall
		.getFunctionNameExpression();
		IASTName name_fcall = fcall_id.getName();
		assertNull(fcall.getParameterExpression());
		
		// void f() {}
		IASTFunctionDefinition fdef = (IASTFunctionDefinition) tu
		.getDeclarations()[2];
		assertTrue(fdef.getDeclarator() instanceof IASTStandardFunctionDeclarator);
		fdtor = (IASTStandardFunctionDeclarator) fdef.getDeclarator();
		IASTName name_fdef = fdtor.getName();
		
		// bindings
		IFunction function_1 = (IFunction) name_fcall.resolveBinding();
		IFunction function_2 = (IFunction) name_f.resolveBinding();
		IFunction function_3 = (IFunction) name_fdef.resolveBinding();
		
		assertNotNull(function_1);
		assertSame(function_1, function_2);
		assertSame(function_2, function_3);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_f.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_f);
		assertEquals(decls[1], name_fdef);
		
		decls = tu.getDeclarationsInAST(gdef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], gdef.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(name_fcall.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_f);
		assertEquals(decls[1], name_fdef);
		
		decls = tu.getDeclarationsInAST(name_fdef.resolveBinding());
		assertEquals(decls.length, 2);
		assertEquals(decls[0], name_f);
		assertEquals(decls[1], name_fdef);
	}
	
	// void f() {                         
	//    for( int i = 0; i < 5; i++ ) {  
	//       i;                           
	//    }                               
	// }  
	public void testForLoop() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		// void f() {
		IASTFunctionDefinition fdef = (IASTFunctionDefinition) tu
		.getDeclarations()[0];
		IASTCompoundStatement compound = (IASTCompoundStatement) fdef.getBody();
		
		// for(
		IASTForStatement for_stmt = (IASTForStatement) compound.getStatements()[0];
		// int i = 0;
		
		IASTSimpleDeclaration initDecl = (IASTSimpleDeclaration) ((IASTDeclarationStatement) for_stmt
				.getInitializerStatement()).getDeclaration();
		IASTDeclarator dtor = initDecl.getDeclarators()[0];
		IASTName name_i = dtor.getName();
		// i < 5;
		IASTBinaryExpression exp = (IASTBinaryExpression) for_stmt
		.getConditionExpression();
		IASTIdExpression id_i = (IASTIdExpression) exp.getOperand1();
		IASTName name_i2 = id_i.getName();
		IASTLiteralExpression lit_5 = (IASTLiteralExpression) exp.getOperand2();
		assertEquals(IASTLiteralExpression.lk_integer_constant, lit_5.getKind());
		// i++ ) {
		IASTUnaryExpression un = (IASTUnaryExpression) for_stmt
		.getIterationExpression();
		IASTIdExpression id_i2 = (IASTIdExpression) un.getOperand();
		IASTName name_i3 = id_i2.getName();
		assertEquals(IASTUnaryExpression.op_postFixIncr, un.getOperator());
		
		// i;
		compound = (IASTCompoundStatement) for_stmt.getBody();
		IASTExpressionStatement exprSt = (IASTExpressionStatement) compound
		.getStatements()[0];
		IASTIdExpression id_i3 = (IASTIdExpression) exprSt.getExpression();
		IASTName name_i4 = id_i3.getName();
		
		// bindings
		IVariable var_1 = (IVariable) name_i4.resolveBinding();
		IVariable var_2 = (IVariable) name_i.resolveBinding();
		IVariable var_3 = (IVariable) name_i2.resolveBinding();
		IVariable var_4 = (IVariable) name_i3.resolveBinding();
		
		assertSame(var_1, var_2);
		assertSame(var_2, var_3);
		assertSame(var_3, var_4);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(fdef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fdef.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(name_i.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
		
		decls = tu.getDeclarationsInAST(name_i2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
		
		decls = tu.getDeclarationsInAST(name_i3.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
		
		decls = tu.getDeclarationsInAST(name_i4.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
	}
	
	// struct A { int x; };    
	// void f(){               
	//    ((struct A *) 1)->x; 
	// }   
	public void testExpressionFieldReference() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTCompositeTypeSpecifier compType = (IASTCompositeTypeSpecifier) simpleDecl
		.getDeclSpecifier();
		IASTSimpleDeclaration decl_x = (IASTSimpleDeclaration) compType
		.getMembers()[0];
		IASTName name_x1 = decl_x.getDeclarators()[0].getName();
		IASTFunctionDefinition fdef = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		IASTCompoundStatement body = (IASTCompoundStatement) fdef.getBody();
		IASTExpressionStatement expStatement = (IASTExpressionStatement) body
		.getStatements()[0];
		IASTFieldReference fieldRef = (IASTFieldReference) expStatement
		.getExpression();
		IASTName name_x2 = fieldRef.getFieldName();
		
		IField x1 = (IField) name_x1.resolveBinding();
		IField x2 = (IField) name_x2.resolveBinding();
		
		assertNotNull(x1);
		assertSame(x1, x2);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(compType.getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], compType.getName());
		
		decls = tu.getDeclarationsInAST(name_x1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x1);
		
		decls = tu.getDeclarationsInAST(fdef.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fdef.getDeclarator().getName());
		
		IASTCastExpression castExpression = (IASTCastExpression) ((IASTUnaryExpression) ((IASTFieldReference) expStatement
				.getExpression()).getFieldOwner()).getOperand();
		IASTElaboratedTypeSpecifier elaboratedTypeSpecifier = ((IASTElaboratedTypeSpecifier) castExpression
				.getTypeId().getDeclSpecifier());
		decls = tu.getDeclarationsInAST(elaboratedTypeSpecifier.getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], compType.getName());
		
		decls = tu.getDeclarationsInAST(name_x2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x1);
	}
	
	// void f() {          
	//    while( 1 ) {     
	//       if( 1 )       
	//          goto end;  
	//    }                
	//    end: ;           
	// }                  
	public void testLabels() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		CNameCollector collector = new CNameCollector();
		tu.accept(collector);
		
		assertEquals(collector.size(), 3);
		IFunction function = (IFunction) collector.getName(0).resolveBinding();
		ILabel label_1 = (ILabel) collector.getName(1).resolveBinding();
		ILabel label_2 = (ILabel) collector.getName(2).resolveBinding();
		assertNotNull(function);
		assertNotNull(label_1);
		assertEquals(label_1, label_2);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(collector.getName(0)
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], collector.getName(0));
		
		decls = tu.getDeclarationsInAST(collector.getName(1).resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], collector.getName(2));
		
		decls = tu.getDeclarationsInAST(collector.getName(2).resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], collector.getName(2));
	}
	
	// typedef struct { } X;
	// int f( X x );
	public void testAnonStruct() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) tu
		.getDeclarations()[1];
		IASTName name_X1 = decl1.getDeclarators()[0].getName();
		IASTName name_f = decl2.getDeclarators()[0].getName();
		IASTName name_X2 = ((IASTNamedTypeSpecifier) ((IASTStandardFunctionDeclarator) decl2
				.getDeclarators()[0]).getParameters()[0].getDeclSpecifier())
				.getName();
		IASTName name_x = ((IASTStandardFunctionDeclarator) decl2
				.getDeclarators()[0]).getParameters()[0].getDeclarator()
				.getName();
		
		IASTName[] decls = tu.getDeclarationsInAST(name_X1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_X1);
		
		decls = tu.getDeclarationsInAST(name_f.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_f);
		
		decls = tu.getDeclarationsInAST(name_X2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_X1);
		
		decls = tu.getDeclarationsInAST(name_x.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
	}
	
	public void testLongLong() throws ParserException {
		IASTTranslationUnit tu = parse("long long x;\n", ParserLanguage.C); //$NON-NLS-1$
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTName name_x = decl1.getDeclarators()[0].getName();
		
		IASTName[] decls = tu.getDeclarationsInAST(name_x.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
	}
	
	// enum hue { red, blue, green };     
	// enum hue col, *cp;                 
	// void f() {                         
	//    col = blue;                     
	//    cp = &col;                      
	//    if( *cp != red )                
	//       return;                      
	// }   
	public void testEnumerations() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		assertEquals(decl1.getDeclarators().length, 0);
		ICASTEnumerationSpecifier enumSpec = (ICASTEnumerationSpecifier) decl1
		.getDeclSpecifier();
		IASTEnumerator e1 = enumSpec.getEnumerators()[0];
		IASTEnumerator e2 = enumSpec.getEnumerators()[1];
		IASTEnumerator e3 = enumSpec.getEnumerators()[2];
		IASTName name_hue = enumSpec.getName();
		
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) tu
		.getDeclarations()[1];
		IASTDeclarator dtor = decl2.getDeclarators()[0];
		IASTName name_col = dtor.getName();
		dtor = decl2.getDeclarators()[1];
		IASTName name_cp = dtor.getName();
		IASTElaboratedTypeSpecifier spec = (IASTElaboratedTypeSpecifier) decl2
		.getDeclSpecifier();
		assertEquals(spec.getKind(), IASTElaboratedTypeSpecifier.k_enum);
		IASTName name_hue2 = spec.getName();
		
		IASTFunctionDefinition fn = (IASTFunctionDefinition) tu
		.getDeclarations()[2];
		IASTCompoundStatement compound = (IASTCompoundStatement) fn.getBody();
		IASTExpressionStatement expStatement1 = (IASTExpressionStatement) compound
		.getStatements()[0];
		IASTBinaryExpression exp = (IASTBinaryExpression) expStatement1
		.getExpression();
		assertEquals(exp.getOperator(), IASTBinaryExpression.op_assign);
		IASTIdExpression id1 = (IASTIdExpression) exp.getOperand1();
		IASTIdExpression id2 = (IASTIdExpression) exp.getOperand2();
		IASTName r_col = id1.getName();
		IASTName r_blue = id2.getName();
		
		IASTExpressionStatement expStatement2 = (IASTExpressionStatement) compound
		.getStatements()[1];
		exp = (IASTBinaryExpression) expStatement2.getExpression();
		assertEquals(exp.getOperator(), IASTBinaryExpression.op_assign);
		id1 = (IASTIdExpression) exp.getOperand1();
		IASTUnaryExpression ue = (IASTUnaryExpression) exp.getOperand2();
		id2 = (IASTIdExpression) ue.getOperand();
		IASTName r_cp = id1.getName();
		IASTName r_col2 = id2.getName();
		
		IASTIfStatement ifStatement = (IASTIfStatement) compound
		.getStatements()[2];
		exp = (IASTBinaryExpression) ifStatement.getConditionExpression();
		ue = (IASTUnaryExpression) exp.getOperand1();
		id1 = (IASTIdExpression) ue.getOperand();
		id2 = (IASTIdExpression) exp.getOperand2();
		
		IASTName r_cp2 = id1.getName();
		IASTName r_red = id2.getName();
		
		IEnumeration hue = (IEnumeration) name_hue.resolveBinding();
		IEnumerator red = (IEnumerator) e1.getName().resolveBinding();
		IEnumerator blue = (IEnumerator) e2.getName().resolveBinding();
		IEnumerator green = (IEnumerator) e3.getName().resolveBinding();
		IVariable col = (IVariable) name_col.resolveBinding();
		IVariable cp = (IVariable) name_cp.resolveBinding();
		IEnumeration hue_2 = (IEnumeration) name_hue2.resolveBinding();
		IVariable col2 = (IVariable) r_col.resolveBinding();
		IEnumerator blue2 = (IEnumerator) r_blue.resolveBinding();
		IVariable cp2 = (IVariable) r_cp.resolveBinding();
		IVariable col3 = (IVariable) r_col2.resolveBinding();
		IVariable cp3 = (IVariable) r_cp2.resolveBinding();
		IEnumerator red2 = (IEnumerator) r_red.resolveBinding();
		
		assertNotNull(hue);
		assertSame(hue, hue_2);
		assertNotNull(red);
		assertNotNull(green);
		assertNotNull(blue);
		assertNotNull(col);
		assertNotNull(cp);
		assertSame(col, col2);
		assertSame(blue, blue2);
		assertSame(cp, cp2);
		assertSame(col, col3);
		assertSame(cp, cp3);
		assertSame(red, red2);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_hue.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_hue);
		
		decls = tu.getDeclarationsInAST(e1.getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], e1.getName());
		
		decls = tu.getDeclarationsInAST(e2.getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], e2.getName());
		
		decls = tu.getDeclarationsInAST(e3.getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], e3.getName());
		
		decls = tu.getDeclarationsInAST(name_hue2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_hue);
		
		decls = tu.getDeclarationsInAST(name_col.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_col);
		
		decls = tu.getDeclarationsInAST(name_cp.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_cp);
		
		decls = tu.getDeclarationsInAST(fn.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fn.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(r_col.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_col);
		
		decls = tu.getDeclarationsInAST(r_blue.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], e2.getName());
		
		decls = tu.getDeclarationsInAST(r_cp.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_cp);
		
		decls = tu.getDeclarationsInAST(r_col2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_col);
		
		decls = tu.getDeclarationsInAST(r_cp2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_cp);
		
		decls = tu.getDeclarationsInAST(r_red.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], e1.getName());
	}
	
	public void testPointerToFunction() throws Exception {
		IASTTranslationUnit tu = parse("int (*pfi)();", ParserLanguage.C); //$NON-NLS-1$
		assertEquals(tu.getDeclarations().length, 1);
		IASTSimpleDeclaration d = (IASTSimpleDeclaration) tu.getDeclarations()[0];
		assertEquals(d.getDeclarators().length, 1);
		IASTStandardFunctionDeclarator f = (IASTStandardFunctionDeclarator) d
		.getDeclarators()[0];
		assertEquals(f.getName().toString(), ""); //$NON-NLS-1$
		assertNotNull(f.getNestedDeclarator());
		assertEquals(f.getNestedDeclarator().getName().toString(), "pfi"); //$NON-NLS-1$
		assertTrue(f.getPointerOperators().length == 0);
		assertFalse(f.getNestedDeclarator().getPointerOperators().length == 0);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(f.getNestedDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], f.getNestedDeclarator().getName());
	}
	
	// int a;       
	// char * b;    
	// const int c; 
	// const char * const d; 
	// const char ** e; 
	// const char * const * const volatile ** const * f; 
	public void testBasicTypes() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IVariable a = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[1];
		IVariable b = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[2];
		IVariable c = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[3];
		IVariable d = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[4];
		IVariable e = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[5];
		IVariable f = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		
		IType t_a_1 = a.getType();
		assertTrue(t_a_1 instanceof IBasicType);
		assertFalse(((IBasicType) t_a_1).isLong());
		assertFalse(((IBasicType) t_a_1).isShort());
		assertFalse(((IBasicType) t_a_1).isSigned());
		assertFalse(((IBasicType) t_a_1).isUnsigned());
		assertEquals(((IBasicType) t_a_1).getType(), IBasicType.t_int);
		
		IType t_b_1 = b.getType();
		assertTrue(t_b_1 instanceof IPointerType);
		IType t_b_2 = ((IPointerType) t_b_1).getType();
		assertTrue(t_b_2 instanceof IBasicType);
		assertEquals(((IBasicType) t_b_2).getType(), IBasicType.t_char);
		
		IType t_c_1 = c.getType();
		assertTrue(t_c_1 instanceof IQualifierType);
		assertTrue(((IQualifierType) t_c_1).isConst());
		IType t_c_2 = ((IQualifierType) t_c_1).getType();
		assertTrue(t_c_2 instanceof IBasicType);
		assertEquals(((IBasicType) t_c_2).getType(), IBasicType.t_int);
		
		IType t_d_1 = d.getType();
		assertTrue(t_d_1 instanceof IPointerType);
		assertTrue(((IPointerType) t_d_1).isConst());
		IType t_d_2 = ((IPointerType) t_d_1).getType();
		assertTrue(t_d_2 instanceof IQualifierType);
		assertTrue(((IQualifierType) t_d_2).isConst());
		IType t_d_3 = ((IQualifierType) t_d_2).getType();
		assertTrue(t_d_3 instanceof IBasicType);
		assertEquals(((IBasicType) t_d_3).getType(), IBasicType.t_char);
		
		IType t_e_1 = e.getType();
		assertTrue(t_e_1 instanceof IPointerType);
		assertFalse(((IPointerType) t_e_1).isConst());
		IType t_e_2 = ((IPointerType) t_e_1).getType();
		assertTrue(t_e_2 instanceof IPointerType);
		assertFalse(((IPointerType) t_e_2).isConst());
		IType t_e_3 = ((IPointerType) t_e_2).getType();
		assertTrue(t_e_3 instanceof IQualifierType);
		assertTrue(((IQualifierType) t_e_3).isConst());
		IType t_e_4 = ((IQualifierType) t_e_3).getType();
		assertTrue(t_e_4 instanceof IBasicType);
		assertEquals(((IBasicType) t_e_4).getType(), IBasicType.t_char);
		
		IType t_f_1 = f.getType();
		assertTrue(t_f_1 instanceof IPointerType);
		assertFalse(((IPointerType) t_f_1).isConst());
		assertFalse(((IPointerType) t_f_1).isVolatile());
		IType t_f_2 = ((IPointerType) t_f_1).getType();
		assertTrue(t_f_2 instanceof IPointerType);
		assertTrue(((IPointerType) t_f_2).isConst());
		assertFalse(((IPointerType) t_f_2).isVolatile());
		IType t_f_3 = ((IPointerType) t_f_2).getType();
		assertTrue(t_f_3 instanceof IPointerType);
		assertFalse(((IPointerType) t_f_3).isConst());
		assertFalse(((IPointerType) t_f_3).isVolatile());
		IType t_f_4 = ((IPointerType) t_f_3).getType();
		assertTrue(t_f_4 instanceof IPointerType);
		assertTrue(((IPointerType) t_f_4).isConst());
		assertTrue(((IPointerType) t_f_4).isVolatile());
		IType t_f_5 = ((IPointerType) t_f_4).getType();
		assertTrue(t_f_5 instanceof IPointerType);
		assertTrue(((IPointerType) t_f_5).isConst());
		assertFalse(((IPointerType) t_f_5).isVolatile());
		IType t_f_6 = ((IPointerType) t_f_5).getType();
		assertTrue(t_f_6 instanceof IQualifierType);
		assertTrue(((IQualifierType) t_f_6).isConst());
		IType t_f_7 = ((IQualifierType) t_f_6).getType();
		assertTrue(t_f_7 instanceof IBasicType);
		assertEquals(((IBasicType) t_f_7).getType(), IBasicType.t_char);
	}
	
	// struct A {} a1;              
	// typedef struct A * AP;       
	// struct A * const a2;         
	// AP a3;   
	public void testCompositeTypes() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTCompositeTypeSpecifier compSpec = (IASTCompositeTypeSpecifier) decl
		.getDeclSpecifier();
		ICompositeType A = (ICompositeType) compSpec.getName().resolveBinding();
		IASTName name_a1 = decl.getDeclarators()[0].getName();
		IVariable a1 = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[1];
		IASTName name_A2 = ((IASTElaboratedTypeSpecifier) decl
				.getDeclSpecifier()).getName();
		IASTName name_AP = decl.getDeclarators()[0].getName();
		ITypedef AP = (ITypedef) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[2];
		IASTName name_A3 = ((IASTElaboratedTypeSpecifier) decl
				.getDeclSpecifier()).getName();
		IVariable a2 = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		IASTName name_a2 = decl.getDeclarators()[0].getName();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[3];
		IVariable a3 = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		IASTName name_a3 = decl.getDeclarators()[0].getName();
		IASTName name_AP2 = ((IASTNamedTypeSpecifier) decl.getDeclSpecifier())
		.getName();
		
		IType t_a1 = a1.getType();
		assertSame(t_a1, A);
		
		IType t_a2 = a2.getType();
		assertTrue(t_a2 instanceof IPointerType);
		assertTrue(((IPointerType) t_a2).isConst());
		assertSame(((IPointerType) t_a2).getType(), A);
		
		IType t_a3 = a3.getType();
		assertSame(t_a3, AP);
		IType t_AP = AP.getType();
		assertTrue(t_AP instanceof IPointerType);
		assertSame(((IPointerType) t_AP).getType(), A);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(compSpec.getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], compSpec.getName());
		
		decls = tu.getDeclarationsInAST(name_a1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a1);
		
		decls = tu.getDeclarationsInAST(name_A2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], compSpec.getName());
		
		decls = tu.getDeclarationsInAST(name_AP.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_AP);
		
		decls = tu.getDeclarationsInAST(name_A3.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], compSpec.getName());
		
		decls = tu.getDeclarationsInAST(name_a2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a2);
		
		decls = tu.getDeclarationsInAST(name_AP2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_AP);
		
		decls = tu.getDeclarationsInAST(name_a3.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a3);
	}
	
	// int a[restrict];       
	// char * b[][];    
	// const char * const c[][][]; 
	public void testArrayTypes() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTName name_a = decl.getDeclarators()[0].getName();
		IVariable a = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[1];
		IASTName name_b = decl.getDeclarators()[0].getName();
		IVariable b = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[2];
		IASTName name_c = decl.getDeclarators()[0].getName();
		IVariable c = (IVariable) decl.getDeclarators()[0].getName()
		.resolveBinding();
		
		IType t_a_1 = a.getType();
		assertTrue(t_a_1 instanceof ICArrayType);
		assertTrue(((ICArrayType) t_a_1).isRestrict());
		IType t_a_2 = ((IArrayType) t_a_1).getType();
		assertTrue(t_a_2 instanceof IBasicType);
		assertEquals(((IBasicType) t_a_2).getType(), IBasicType.t_int);
		
		IType t_b_1 = b.getType();
		assertTrue(t_b_1 instanceof IArrayType);
		IType t_b_2 = ((IArrayType) t_b_1).getType();
		assertTrue(t_b_2 instanceof IArrayType);
		IType t_b_3 = ((IArrayType) t_b_2).getType();
		assertTrue(t_b_3 instanceof IPointerType);
		IType t_b_4 = ((IPointerType) t_b_3).getType();
		assertTrue(t_b_4 instanceof IBasicType);
		assertEquals(((IBasicType) t_b_4).getType(), IBasicType.t_char);
		
		IType t_c_1 = c.getType();
		assertTrue(t_c_1 instanceof IArrayType);
		IType t_c_2 = ((IArrayType) t_c_1).getType();
		assertTrue(t_c_2 instanceof IArrayType);
		IType t_c_3 = ((IArrayType) t_c_2).getType();
		assertTrue(t_c_3 instanceof IArrayType);
		IType t_c_4 = ((IArrayType) t_c_3).getType();
		assertTrue(t_c_4 instanceof IPointerType);
		assertTrue(((IPointerType) t_c_4).isConst());
		IType t_c_5 = ((IPointerType) t_c_4).getType();
		assertTrue(t_c_5 instanceof IQualifierType);
		assertTrue(((IQualifierType) t_c_5).isConst());
		IType t_c_6 = ((IQualifierType) t_c_5).getType();
		assertTrue(t_c_6 instanceof IBasicType);
		assertEquals(((IBasicType) t_c_6).getType(), IBasicType.t_char);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_a.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_a);
		
		decls = tu.getDeclarationsInAST(name_b.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_b);
		
		decls = tu.getDeclarationsInAST(name_c.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_c);
	}
	
	// struct A;                           
	// int * f( int i, char c );           
	// void ( *g ) ( struct A * );         
	// void (* (*h)(struct A**) ) ( int d ); 
	public void testFunctionTypes() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTElaboratedTypeSpecifier elabSpec = (IASTElaboratedTypeSpecifier) decl
		.getDeclSpecifier();
		ICompositeType A = (ICompositeType) elabSpec.getName().resolveBinding();
		IASTName name_A1 = elabSpec.getName();
		assertTrue(name_A1.isDeclaration());
		
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[1];
		IFunction f = (IFunction) decl.getDeclarators()[0].getName()
		.resolveBinding();
		IASTName name_f = decl.getDeclarators()[0].getName();
		IASTName name_i = ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getParameters()[0].getDeclarator()
				.getName();
		IASTName name_c = ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getParameters()[1].getDeclarator()
				.getName();
		
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[2];
		IVariable g = (IVariable) decl.getDeclarators()[0]
		                                                .getNestedDeclarator().getName().resolveBinding();
		IASTName name_g = decl.getDeclarators()[0].getNestedDeclarator()
		.getName();
		IASTName name_A2 = ((IASTElaboratedTypeSpecifier) ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getParameters()[0].getDeclSpecifier())
				.getName();
		
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[3];
		IVariable h = (IVariable) decl.getDeclarators()[0]
		                                                .getNestedDeclarator().getNestedDeclarator().getName()
		                                                .resolveBinding();
		IASTName name_h = decl.getDeclarators()[0].getNestedDeclarator()
		.getNestedDeclarator().getName();
		IASTName name_A3 = ((IASTElaboratedTypeSpecifier) ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0].getNestedDeclarator()).getParameters()[0]
				                                                            .getDeclSpecifier()).getName();
		IASTName name_d = ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getParameters()[0].getDeclarator()
				.getName();
		
		IFunctionType t_f = f.getType();
		IType t_f_return = t_f.getReturnType();
		assertTrue(t_f_return instanceof IPointerType);
		assertTrue(((IPointerType) t_f_return).getType() instanceof IBasicType);
		IType[] t_f_params = t_f.getParameterTypes();
		assertEquals(t_f_params.length, 2);
		assertTrue(t_f_params[0] instanceof IBasicType);
		assertTrue(t_f_params[1] instanceof IBasicType);
		
		// g is a pointer to a function that returns void and has 1 parameter
		// struct A *
		IType t_g = g.getType();
		assertTrue(t_g instanceof IPointerType);
		assertTrue(((IPointerType) t_g).getType() instanceof IFunctionType);
		IFunctionType t_g_func = (IFunctionType) ((IPointerType) t_g).getType();
		IType t_g_func_return = t_g_func.getReturnType();
		assertTrue(t_g_func_return instanceof IBasicType);
		IType[] t_g_func_params = t_g_func.getParameterTypes();
		assertEquals(t_g_func_params.length, 1);
		IType t_g_func_p1 = t_g_func_params[0];
		assertTrue(t_g_func_p1 instanceof IPointerType);
		assertSame(((IPointerType) t_g_func_p1).getType(), A);
		
		// h is a pointer to a function that returns a pointer to a function
		// the returned pointer to function returns void and takes 1 parameter
		// int
		// the *h function takes 1 parameter struct A**
		IType t_h = h.getType();
		assertTrue(t_h instanceof IPointerType);
		assertTrue(((IPointerType) t_h).getType() instanceof IFunctionType);
		IFunctionType t_h_func = (IFunctionType) ((IPointerType) t_h).getType();
		IType t_h_func_return = t_h_func.getReturnType();
		IType[] t_h_func_params = t_h_func.getParameterTypes();
		assertEquals(t_h_func_params.length, 1);
		IType t_h_func_p1 = t_h_func_params[0];
		assertTrue(t_h_func_p1 instanceof IPointerType);
		assertTrue(((IPointerType) t_h_func_p1).getType() instanceof IPointerType);
		assertSame(((IPointerType) ((IPointerType) t_h_func_p1).getType())
				.getType(), A);
		
		assertTrue(t_h_func_return instanceof IPointerType);
		IFunctionType h_return = (IFunctionType) ((IPointerType) t_h_func_return)
		.getType();
		IType h_r = h_return.getReturnType();
		IType[] h_ps = h_return.getParameterTypes();
		assertTrue(h_r instanceof IBasicType);
		assertEquals(h_ps.length, 1);
		assertTrue(h_ps[0] instanceof IBasicType);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_A1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_A1);
		
		decls = tu.getDeclarationsInAST(name_f.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_f);
		
		decls = tu.getDeclarationsInAST(name_i.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_i);
		
		decls = tu.getDeclarationsInAST(name_c.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_c);
		
		decls = tu.getDeclarationsInAST(name_g.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_g);
		
		decls = tu.getDeclarationsInAST(name_A2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_A1);
		
		decls = tu.getDeclarationsInAST(name_h.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_h);
		
		decls = tu.getDeclarationsInAST(name_A3.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_A1);
		
		assertNull("Expected null, got "+name_d.resolveBinding(), name_d.resolveBinding());
	}
	
	// typedef struct {
	//  int x;
	//  int y;
	// } Coord;
	// typedef struct {
	// Coord *pos;
	// int width;
	// } Point;
	// int main(int argc, char *argv[])
	// {
	// Coord xy = {.y = 10, .x = 11};
	// Point point = {.width = 100, .pos = &xy};
	// }
	public void testDesignatedInitializers() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		assertNotNull(tu);
		IASTDeclaration[] declarations = tu.getDeclarations();
		IASTName name_Coord = ((IASTSimpleDeclaration) declarations[0])
		.getDeclarators()[0].getName();
		IASTName name_x = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) ((IASTSimpleDeclaration) declarations[0])
				.getDeclSpecifier()).getMembers()[0]).getDeclarators()[0]
				                                                       .getName();
		IASTName name_y = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) ((IASTSimpleDeclaration) declarations[0])
				.getDeclSpecifier()).getMembers()[1]).getDeclarators()[0]
				                                                       .getName();
		IASTName name_Point = ((IASTSimpleDeclaration) declarations[1])
		.getDeclarators()[0].getName();
		IASTName name_pos = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) ((IASTSimpleDeclaration) declarations[1])
				.getDeclSpecifier()).getMembers()[0]).getDeclarators()[0]
				                                                       .getName();
		IASTName name_width = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) ((IASTSimpleDeclaration) declarations[1])
				.getDeclSpecifier()).getMembers()[1]).getDeclarators()[0]
				                                                       .getName();
		IASTFunctionDefinition main = (IASTFunctionDefinition) declarations[2];
		IASTStatement[] statements = ((IASTCompoundStatement) main.getBody())
		.getStatements();
		
		IASTSimpleDeclaration xy = (IASTSimpleDeclaration) ((IASTDeclarationStatement) statements[0])
		.getDeclaration();
		IASTName name_Coord2 = ((IASTNamedTypeSpecifier) xy.getDeclSpecifier())
		.getName();
		IASTName name_xy = xy.getDeclarators()[0].getName();
		IASTDeclarator declarator_xy = xy.getDeclarators()[0];
		IASTInitializer[] initializers1 = ((IASTInitializerList) declarator_xy
				.getInitializer()).getInitializers();
		IASTName name_y2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) initializers1[0])
				.getDesignators()[0]).getName();
		
		// test bug 87649
		assertEquals(((ASTNode) (ICASTDesignatedInitializer) initializers1[0])
				.getLength(), 7);
		
		IASTName name_x2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) initializers1[1])
				.getDesignators()[0]).getName();
		
		IASTSimpleDeclaration point = (IASTSimpleDeclaration) ((IASTDeclarationStatement) statements[1])
		.getDeclaration();
		IASTName name_Point2 = ((IASTNamedTypeSpecifier) point
				.getDeclSpecifier()).getName();
		IASTName name_point = point.getDeclarators()[0].getName();
		IASTDeclarator declarator_point = point.getDeclarators()[0];
		IASTInitializer[] initializers2 = ((IASTInitializerList) declarator_point
				.getInitializer()).getInitializers();
		IASTName name_width2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) initializers2[0])
				.getDesignators()[0]).getName();
		IASTName name_pos2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) initializers2[1])
				.getDesignators()[0]).getName();
		IASTName name_xy2 = ((IASTIdExpression) ((IASTUnaryExpression) ((IASTInitializerExpression) ((ICASTDesignatedInitializer) initializers2[1])
				.getOperandInitializer()).getExpression()).getOperand())
				.getName();
		
		for (int i = 0; i < 2; ++i) {
			ICASTDesignatedInitializer designatedInitializer = (ICASTDesignatedInitializer) initializers1[i];
			assertEquals(designatedInitializer.getDesignators().length, 1);
			ICASTFieldDesignator fieldDesignator = (ICASTFieldDesignator) designatedInitializer
			.getDesignators()[0];
			assertNotNull(fieldDesignator.getName().toString());
		}
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(name_Coord2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_Coord);
		
		decls = tu.getDeclarationsInAST(name_xy.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_xy);
		
		decls = tu.getDeclarationsInAST(name_y2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_y);
		
		decls = tu.getDeclarationsInAST(name_x2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_x);
		
		decls = tu.getDeclarationsInAST(name_Point2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_Point);
		
		decls = tu.getDeclarationsInAST(name_point.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_point);
		
		decls = tu.getDeclarationsInAST(name_width2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_width);
		
		decls = tu.getDeclarationsInAST(name_pos2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_pos);
		
		decls = tu.getDeclarationsInAST(name_xy2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_xy);
	}
	
	// struct S {
	//  int a;
	//  int b;
	// } s;
	// int f() {
	// struct S s = {.a=1,.b=2};
	// }
	public void testMoregetDeclarationsInAST1() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration S_decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTFunctionDefinition f_def = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		
		IASTName a1 = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) S_decl
				.getDeclSpecifier()).getMembers()[0]).getDeclarators()[0]
				                                                       .getName();
		IASTName b1 = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) S_decl
				.getDeclSpecifier()).getMembers()[1]).getDeclarators()[0]
				                                                       .getName();
		IASTName a2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) ((IASTInitializerList) ((IASTSimpleDeclaration) ((IASTDeclarationStatement) ((IASTCompoundStatement) f_def
				.getBody()).getStatements()[0]).getDeclaration())
				.getDeclarators()[0].getInitializer()).getInitializers()[0])
				.getDesignators()[0]).getName();
		IASTName b2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) ((IASTInitializerList) ((IASTSimpleDeclaration) ((IASTDeclarationStatement) ((IASTCompoundStatement) f_def
				.getBody()).getStatements()[0]).getDeclaration())
				.getDeclarators()[0].getInitializer()).getInitializers()[1])
				.getDesignators()[0]).getName();
		
		assertEquals(a1.resolveBinding(), a2.resolveBinding());
		assertEquals(b1.resolveBinding(), b2.resolveBinding());
		
		IASTName[] decls = tu.getDeclarationsInAST(a1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(a1, decls[0]);
		
		decls = tu.getDeclarationsInAST(b1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(b1, decls[0]);
	}
	
	//  struct S { 
	//  int a; 
	//  int b; 
	// } s = {.a=1,.b=2};
	public void testMoregetDeclarationsInAST2() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration S_decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		
		IASTName a1 = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) S_decl
				.getDeclSpecifier()).getMembers()[0]).getDeclarators()[0]
				                                                       .getName();
		IASTName b1 = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) S_decl
				.getDeclSpecifier()).getMembers()[1]).getDeclarators()[0]
				                                                       .getName();
		IASTName a2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) ((IASTInitializerList) S_decl
				.getDeclarators()[0].getInitializer()).getInitializers()[0])
				.getDesignators()[0]).getName();
		IASTName b2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) ((IASTInitializerList) S_decl
				.getDeclarators()[0].getInitializer()).getInitializers()[1])
				.getDesignators()[0]).getName();
		
		assertEquals(a1.resolveBinding(), a2.resolveBinding());
		assertEquals(b1.resolveBinding(), b2.resolveBinding());
		
		IASTName[] decls = tu.getDeclarationsInAST(a1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(a1, decls[0]);
		
		decls = tu.getDeclarationsInAST(b1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(b1, decls[0]);
	}
	
	//  typedef struct S { 
	//  int a; 
	//  int b; 
	// } s;
	// typedef s t;
	// typedef t y;
	// y x = {.a=1,.b=2};
	public void testMoregetDeclarationsInAST3() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration S_decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IASTSimpleDeclaration x_decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[3];
		
		IASTName a1 = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) S_decl
				.getDeclSpecifier()).getMembers()[0]).getDeclarators()[0]
				                                                       .getName();
		IASTName b1 = ((IASTSimpleDeclaration) ((IASTCompositeTypeSpecifier) S_decl
				.getDeclSpecifier()).getMembers()[1]).getDeclarators()[0]
				                                                       .getName();
		IASTName a2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) ((IASTInitializerList) x_decl
				.getDeclarators()[0].getInitializer()).getInitializers()[0])
				.getDesignators()[0]).getName();
		IASTName b2 = ((ICASTFieldDesignator) ((ICASTDesignatedInitializer) ((IASTInitializerList) x_decl
				.getDeclarators()[0].getInitializer()).getInitializers()[1])
				.getDesignators()[0]).getName();
		
		assertEquals(a1.resolveBinding(), a2.resolveBinding());
		assertEquals(b1.resolveBinding(), b2.resolveBinding());
		
		IASTName[] decls = tu.getDeclarationsInAST(a1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(a1, decls[0]);
		
		decls = tu.getDeclarationsInAST(b1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(b1, decls[0]);
	}
	
	public void testFnReturningPtrToFn() throws Exception {
		IASTTranslationUnit tu = parse(
				"void ( * f( int ) )(){}", ParserLanguage.C); //$NON-NLS-1$
		
		IASTFunctionDefinition def = (IASTFunctionDefinition) tu.getDeclarations()[0];
		final IASTName fname = def.getDeclarator().getName();
		IFunction f = (IFunction) fname.resolveBinding();
		
		IFunctionType ft = f.getType();
		assertTrue(ft.getReturnType() instanceof IPointerType);
		assertTrue(((IPointerType) ft.getReturnType()).getType() instanceof IFunctionType);
		assertEquals(ft.getParameterTypes().length, 1);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(f);
		assertEquals(decls.length, 1);
		assertEquals(decls[0], fname);
	}
	
	// test C99: 6.7.5.3-7 A declaration of a parameter as ''array of type''
	// shall be adjusted to ''qualified pointer to
	// type'', where the type qualifiers (if any) are those specified within the
	// [ and ] of the
	// array type derivation.
	public void testArrayTypeToQualifiedPointerTypeParm() throws Exception {
		IASTTranslationUnit tu = parse(
				"void f(int parm[const 3]);", ParserLanguage.C); //$NON-NLS-1$
		
		IASTSimpleDeclaration def = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IFunction f = (IFunction) def.getDeclarators()[0].getName()
		.resolveBinding();
		
		IFunctionType ft = f.getType();
		assertTrue(ft.getParameterTypes()[0] instanceof IPointerType);
		assertTrue(((IPointerType) ft.getParameterTypes()[0]).isConst());
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName name_parm = ((IASTStandardFunctionDeclarator) def
				.getDeclarators()[0]).getParameters()[0].getDeclarator()
				.getName();
		IASTName[] decls = tu.getDeclarationsInAST(name_parm.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_parm);
	}
	
	// int f() {}
	// int *f2() {}
	// int (* f3())() {}
	public void testFunctionDefTypes() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C); 
		
		IASTFunctionDefinition def1 = (IASTFunctionDefinition) tu
		.getDeclarations()[0];
		IFunction f = (IFunction) def1.getDeclarator().getName()
		.resolveBinding();
		IASTFunctionDefinition def2 = (IASTFunctionDefinition) tu
		.getDeclarations()[1];
		IFunction f2 = (IFunction) def2.getDeclarator().getName()
		.resolveBinding();
		IASTFunctionDefinition def3 = (IASTFunctionDefinition) tu
		.getDeclarations()[2];
		IFunction f3 = (IFunction) def3.getDeclarator().getName()
		.resolveBinding();
		
		IFunctionType ft = f.getType();
		IFunctionType ft2 = f2.getType();
		IFunctionType ft3 = f3.getType();
		
		assertTrue(ft.getReturnType() instanceof IBasicType);
		assertTrue(ft2.getReturnType() instanceof IPointerType);
		assertTrue(((IPointerType) ft2.getReturnType()).getType() instanceof IBasicType);
		assertTrue(ft3.getReturnType() instanceof IPointerType);
		assertTrue(((IPointerType) ft3.getReturnType()).getType() instanceof IFunctionType);
		assertTrue(((IFunctionType) ((IPointerType) ft3.getReturnType())
				.getType()).getReturnType() instanceof IBasicType);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu.getDeclarationsInAST(def1.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], def1.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(def2.getDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], def2.getDeclarator().getName());
		
		decls = tu.getDeclarationsInAST(def3.getDeclarator().getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], def3.getDeclarator().getName());
	}
	
	// any parameter to type function returning T is adjusted to be pointer to
	// function returning T
	public void testParmToFunction() throws Exception {
		IASTTranslationUnit tu = parse(
				"int f(int g(void)) { return g();}", ParserLanguage.C); //$NON-NLS-1$
		
		IASTFunctionDefinition def = (IASTFunctionDefinition) tu
		.getDeclarations()[0];
		IFunction f = (IFunction) def.getDeclarator().getName()
		.resolveBinding();
		
		IType ft = ((CFunction) f).getType();
		assertTrue(ft instanceof IFunctionType);
		IType gt_1 = ((IFunctionType) ft).getParameterTypes()[0];
		assertTrue(gt_1 instanceof IPointerType);
		IType gt_2 = ((IPointerType) gt_1).getType();
		assertTrue(gt_2 instanceof IFunctionType);
		IType gt_ret = ((IFunctionType) gt_2).getReturnType();
		assertTrue(gt_ret instanceof IBasicType);
		assertEquals(((IBasicType) gt_ret).getType(), IBasicType.t_int);
		IType gt_parm = ((IFunctionType) gt_2).getParameterTypes()[0];
		assertTrue(gt_parm instanceof IBasicType);
		assertEquals(((IBasicType) gt_parm).getType(), IBasicType.t_void);
		
		// test tu.getDeclarationsInAST(IBinding)
		assertTrue(def.getDeclarator() instanceof IASTStandardFunctionDeclarator);
		IASTName name_g = ((IASTStandardFunctionDeclarator) def.getDeclarator())
		.getParameters()[0].getDeclarator().getName();
		IASTName name_g_call = ((IASTIdExpression) ((IASTFunctionCallExpression) ((IASTReturnStatement) ((IASTCompoundStatement) def
				.getBody()).getStatements()[0]).getReturnValue())
				.getFunctionNameExpression()).getName();
		IASTName[] decls = tu.getDeclarationsInAST(name_g_call.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_g);
	}
	
	public void testArrayPointerFunction() throws Exception {
		IASTTranslationUnit tu = parse(
				"int (*v[])(int *x, int *y);", ParserLanguage.C); //$NON-NLS-1$
		
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		IVariable v = (IVariable) ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getNestedDeclarator().getName()
				.resolveBinding();
		
		IType vt_1 = v.getType();
		assertTrue(vt_1 instanceof IArrayType);
		IType vt_2 = ((IArrayType) vt_1).getType();
		assertTrue(vt_2 instanceof IPointerType);
		IType vt_3 = ((IPointerType) vt_2).getType();
		assertTrue(vt_3 instanceof IFunctionType);
		IType vt_ret = ((IFunctionType) vt_3).getReturnType();
		assertTrue(vt_ret instanceof IBasicType);
		assertEquals(((IBasicType) vt_ret).getType(), IBasicType.t_int);
		assertEquals(((IFunctionType) vt_3).getParameterTypes().length, 2);
		IType vpt_1 = ((IFunctionType) vt_3).getParameterTypes()[0];
		assertTrue(vpt_1 instanceof IPointerType);
		IType vpt_1_2 = ((IPointerType) vpt_1).getType();
		assertTrue(vpt_1_2 instanceof IBasicType);
		assertEquals(((IBasicType) vpt_1_2).getType(), IBasicType.t_int);
		IType vpt_2 = ((IFunctionType) vt_3).getParameterTypes()[0];
		assertTrue(vpt_2 instanceof IPointerType);
		IType vpt_2_2 = ((IPointerType) vpt_1).getType();
		assertTrue(vpt_2_2 instanceof IBasicType);
		assertEquals(((IBasicType) vpt_2_2).getType(), IBasicType.t_int);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName[] decls = tu
		.getDeclarationsInAST(((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getNestedDeclarator().getName()
				.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], ((IASTStandardFunctionDeclarator) decl
				.getDeclarators()[0]).getNestedDeclarator().getName());
	}
	
	// typedef void DWORD;
	// typedef DWORD v;
	// v signal(int);
	public void testTypedefExample4a() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		ITypedef dword = (ITypedef) decl1.getDeclarators()[0].getName()
		.resolveBinding();
		IType dword_t = dword.getType();
		assertTrue(dword_t instanceof IBasicType);
		assertEquals(((IBasicType) dword_t).getType(), IBasicType.t_void);
		
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) tu
		.getDeclarations()[1];
		ITypedef v = (ITypedef) decl2.getDeclarators()[0].getName()
		.resolveBinding();
		IType v_t_1 = v.getType();
		assertTrue(v_t_1 instanceof ITypedef);
		IType v_t_2 = ((ITypedef) v_t_1).getType();
		assertTrue(v_t_2 instanceof IBasicType);
		assertEquals(((IBasicType) v_t_2).getType(), IBasicType.t_void);
		
		IASTSimpleDeclaration decl3 = (IASTSimpleDeclaration) tu
		.getDeclarations()[2];
		IFunction signal = (IFunction) decl3.getDeclarators()[0].getName()
		.resolveBinding();
		IFunctionType signal_t = signal.getType();
		IType signal_ret = signal_t.getReturnType();
		assertTrue(signal_ret instanceof ITypedef);
		IType signal_ret2 = ((ITypedef) signal_ret).getType();
		assertTrue(signal_ret2 instanceof ITypedef);
		IType signal_ret3 = ((ITypedef) signal_ret2).getType();
		assertTrue(signal_ret3 instanceof IBasicType);
		assertEquals(((IBasicType) signal_ret3).getType(), IBasicType.t_void);
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName name_DWORD = decl1.getDeclarators()[0].getName();
		IASTName name_v = decl2.getDeclarators()[0].getName();
		
		IASTName[] decls = tu.getDeclarationsInAST(name_DWORD.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_DWORD);
		
		decls = tu.getDeclarationsInAST(((IASTNamedTypeSpecifier) decl2
				.getDeclSpecifier()).getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_DWORD);
		
		decls = tu.getDeclarationsInAST(((IASTNamedTypeSpecifier) decl3
				.getDeclSpecifier()).getName().resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_v);
	}
	
	// typedef void DWORD;
	// typedef DWORD (*pfv)(int);
	// pfv signal(int, pfv);
	public void testTypedefExample4b() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl1 = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		ITypedef dword = (ITypedef) decl1.getDeclarators()[0].getName()
		.resolveBinding();
		IType dword_t = dword.getType();
		assertTrue(dword_t instanceof IBasicType);
		assertEquals(((IBasicType) dword_t).getType(), IBasicType.t_void);
		
		IASTSimpleDeclaration decl2 = (IASTSimpleDeclaration) tu
		.getDeclarations()[1];
		ITypedef pfv = (ITypedef) decl2.getDeclarators()[0]
		                                                 .getNestedDeclarator().getName().resolveBinding();
		IType pfv_t_1 = pfv.getType();
		assertTrue(pfv_t_1 instanceof IPointerType);
		IType pfv_t_2 = ((IPointerType) pfv_t_1).getType();
		assertTrue(pfv_t_2 instanceof IFunctionType);
		IType pfv_t_2_ret_1 = ((IFunctionType) pfv_t_2).getReturnType();
		assertTrue(pfv_t_2_ret_1 instanceof ITypedef);
		IType pfv_t_2_ret_2 = ((ITypedef) pfv_t_2_ret_1).getType();
		assertTrue(pfv_t_2_ret_2 instanceof IBasicType);
		assertEquals(((IBasicType) pfv_t_2_ret_2).getType(), IBasicType.t_void);
		assertTrue(((ITypedef) pfv_t_2_ret_1).getName().equals("DWORD")); //$NON-NLS-1$
		IType pfv_t_2_parm = ((IFunctionType) pfv_t_2).getParameterTypes()[0];
		assertTrue(pfv_t_2_parm instanceof IBasicType);
		assertEquals(((IBasicType) pfv_t_2_parm).getType(), IBasicType.t_int);
		
		IASTSimpleDeclaration decl3 = (IASTSimpleDeclaration) tu
		.getDeclarations()[2];
		IFunction signal = (IFunction) decl3.getDeclarators()[0].getName()
		.resolveBinding();
		IFunctionType signal_t = signal.getType();
		IType signal_ret_1 = signal_t.getReturnType();
		assertTrue(signal_ret_1 instanceof ITypedef);
		IType signal_ret_2 = ((ITypedef) signal_ret_1).getType();
		assertTrue(signal_ret_2 instanceof IPointerType);
		IType signal_ret_3 = ((IPointerType) signal_ret_2).getType();
		assertTrue(signal_ret_3 instanceof IFunctionType);
		IType signal_ret_ret_1 = ((IFunctionType) signal_ret_3).getReturnType();
		assertTrue(signal_ret_ret_1 instanceof ITypedef);
		IType signal_ret_ret_2 = ((ITypedef) signal_ret_ret_1).getType();
		assertTrue(signal_ret_ret_2 instanceof IBasicType);
		assertEquals(((IBasicType) signal_ret_ret_2).getType(),
				IBasicType.t_void);
		assertTrue(((ITypedef) signal_ret_ret_1).getName().equals("DWORD")); //$NON-NLS-1$
		
		IType signal_parm_t1 = signal_t.getParameterTypes()[0];
		assertTrue(signal_parm_t1 instanceof IBasicType);
		assertEquals(((IBasicType) signal_parm_t1).getType(), IBasicType.t_int);
		IType signal_parm_t2 = signal_t.getParameterTypes()[1];
		assertTrue(signal_parm_t2 instanceof ITypedef);
		IType signal_parm_t2_1 = ((ITypedef) signal_parm_t2).getType();
		assertTrue(signal_parm_t2_1 instanceof IPointerType);
		IType signal_parm_t2_2 = ((IPointerType) signal_parm_t2_1).getType();
		assertTrue(signal_parm_t2_2 instanceof IFunctionType);
		IType signal_parm_t2_ret_1 = ((IFunctionType) signal_parm_t2_2)
		.getReturnType();
		assertTrue(signal_parm_t2_ret_1 instanceof ITypedef);
		IType signal_parm_t2_ret_2 = ((ITypedef) signal_parm_t2_ret_1)
		.getType();
		assertTrue(signal_parm_t2_ret_2 instanceof IBasicType);
		assertEquals(((IBasicType) signal_parm_t2_ret_2).getType(),
				IBasicType.t_void);
		assertTrue(((ITypedef) signal_parm_t2_ret_1).getName().equals("DWORD")); //$NON-NLS-1$
		
		// test tu.getDeclarationsInAST(IBinding)
		IASTName name_pfv = decl2.getDeclarators()[0].getNestedDeclarator()
		.getName();
		IASTName name_pfv1 = ((IASTNamedTypeSpecifier) decl3.getDeclSpecifier())
		.getName();
		IASTName name_pfv2 = ((IASTNamedTypeSpecifier) ((IASTStandardFunctionDeclarator) decl3
				.getDeclarators()[0]).getParameters()[1].getDeclSpecifier())
				.getName();
		
		IASTName[] decls = tu.getDeclarationsInAST(name_pfv1.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_pfv);
		
		decls = tu.getDeclarationsInAST(name_pfv2.resolveBinding());
		assertEquals(decls.length, 1);
		assertEquals(decls[0], name_pfv);
	}
	
	// typedef void fv(int), (*pfv)(int);
	// void (*signal1(int, void (*)(int)))(int);
	// fv *signal2(int, fv *);
	// pfv signal3(int, pfv);
	public void testTypedefExample4c() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		
		IASTSimpleDeclaration decl = (IASTSimpleDeclaration) tu
		.getDeclarations()[0];
		ITypedef fv = (ITypedef) decl.getDeclarators()[0].getName()
		.resolveBinding();
		ITypedef pfv = (ITypedef) decl.getDeclarators()[1]
		                                                .getNestedDeclarator().getName().resolveBinding();
		
		IType fv_t = fv.getType();
		assertEquals(((IBasicType) ((IFunctionType) fv_t).getReturnType())
				.getType(), IBasicType.t_void);
		assertEquals(
				((IBasicType) ((IFunctionType) fv_t).getParameterTypes()[0])
				.getType(), IBasicType.t_int);
		
		IType pfv_t = pfv.getType();
		assertEquals(((IBasicType) ((IFunctionType) ((IPointerType) pfv_t)
				.getType()).getReturnType()).getType(), IBasicType.t_void);
		assertEquals(((IBasicType) ((IFunctionType) ((IPointerType) pfv
				.getType()).getType()).getParameterTypes()[0]).getType(),
				IBasicType.t_int);
		
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[1];
		IFunction signal1 = (IFunction) decl.getDeclarators()[0]
		                                                      .getNestedDeclarator().getName().resolveBinding();
		IType signal1_t = signal1.getType();
		
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[2];
		IFunction signal2 = (IFunction) decl.getDeclarators()[0].getName()
		.resolveBinding();
		IType signal2_t = signal2.getType();
		
		decl = (IASTSimpleDeclaration) tu.getDeclarations()[3];
		IFunction signal3 = (IFunction) decl.getDeclarators()[0].getName()
		.resolveBinding();
		IType signal3_t = signal3.getType();
		
		assertEquals(
				((IBasicType) ((IFunctionType) ((IPointerType) ((IFunctionType) signal1_t)
						.getReturnType()).getType()).getReturnType()).getType(),
						IBasicType.t_void);
		assertEquals(((IBasicType) ((IFunctionType) signal1_t)
				.getParameterTypes()[0]).getType(), IBasicType.t_int);
		assertEquals(
				((IBasicType) ((IFunctionType) ((IPointerType) ((IFunctionType) signal1_t)
						.getParameterTypes()[1]).getType()).getReturnType())
						.getType(), IBasicType.t_void);
		assertEquals(
				((IBasicType) ((IFunctionType) ((IPointerType) ((IFunctionType) signal1_t)
						.getParameterTypes()[1]).getType()).getParameterTypes()[0])
						.getType(), IBasicType.t_int);
		
		assertEquals(
				((IBasicType) ((IFunctionType) ((ITypedef) ((IPointerType) ((IFunctionType) signal2_t)
						.getReturnType()).getType()).getType()).getReturnType())
						.getType(), IBasicType.t_void);
		assertEquals(((IBasicType) ((IFunctionType) signal2_t)
				.getParameterTypes()[0]).getType(), IBasicType.t_int);
		assertEquals(
				((IBasicType) ((IFunctionType) ((ITypedef) ((IPointerType) ((IFunctionType) signal2_t)
						.getParameterTypes()[1]).getType()).getType())
						.getReturnType()).getType(), IBasicType.t_void);
		assertEquals(
				((IBasicType) ((IFunctionType) ((ITypedef) ((IPointerType) ((IFunctionType) signal2_t)
						.getParameterTypes()[1]).getType()).getType())
						.getParameterTypes()[0]).getType(), IBasicType.t_int);
		
		assertEquals(
				((IBasicType) ((IFunctionType) ((IPointerType) ((ITypedef) ((IFunctionType) signal3_t)
						.getReturnType()).getType()).getType()).getReturnType())
						.getType(), IBasicType.t_void);
		assertEquals(((IBasicType) ((IFunctionType) signal3_t)
				.getParameterTypes()[0]).getType(), IBasicType.t_int);
		assertEquals(
				((IBasicType) ((IFunctionType) ((IPointerType) ((ITypedef) ((IFunctionType) signal3_t)
						.getParameterTypes()[1]).getType()).getType())
						.getReturnType()).getType(), IBasicType.t_void);
		assertEquals(
				((IBasicType) ((IFunctionType) ((IPointerType) ((ITypedef) ((IFunctionType) signal3_t)
						.getParameterTypes()[1]).getType()).getType())
						.getParameterTypes()[0]).getType(), IBasicType.t_int);
		
	}
	
	// const int x = 10;
	// int y [ const static x ];
	public void testBug80992() throws Exception {
		ICASTArrayModifier mod = (ICASTArrayModifier) ((IASTArrayDeclarator) ((IASTSimpleDeclaration) parse(
				getAboveComment(), ParserLanguage.C).getDeclarations()[1])
				.getDeclarators()[0]).getArrayModifiers()[0];
		assertTrue(mod.isConst());
		assertTrue(mod.isStatic());
		assertFalse(mod.isRestrict());
		assertFalse(mod.isVolatile());
		assertFalse(mod.isVariableSized());
	}
	
	
	// int y ( int [ const *] );
	public void testBug80978() throws Exception {
		ICASTArrayModifier mod = (ICASTArrayModifier) ((IASTArrayDeclarator) ((IASTStandardFunctionDeclarator) ((IASTSimpleDeclaration) parse(
				getAboveComment(), ParserLanguage.C).getDeclarations()[0])
				.getDeclarators()[0]).getParameters()[0].getDeclarator())
				.getArrayModifiers()[0];
		assertTrue(mod.isConst());
		assertTrue(mod.isVariableSized());
		assertFalse(mod.isStatic());
		assertFalse(mod.isRestrict());
		assertFalse(mod.isVolatile());
	}
	
	//AJN: bug 77383 don't do external variables	  
	//    // void f() {               
	//    //    if( a == 0 )          
	//    //       a = a + 3;         
	//    // }                        
	//    public void testExternalVariable() throws Exception {
	//        IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
	//        CNameCollector col = new CNameCollector();
	//        tu.accept(col);
	//
	//        IVariable a = (IVariable) col.getName(1).resolveBinding();
	//        assertNotNull(a);
	//        assertTrue(a instanceof ICExternalBinding);
	//        assertInstances(col, a, 3);
	//    }
	
	// void f() {               
	//    int a = 1;            
	//    if( a == 0 )          
	//       g( a );            
	//    if( a < 0 )           
	//       g( a >> 1 );       
	//    if( a > 0 )           
	//       g( *(&a + 2) );    
	// }            
	public void testExternalDefs() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IVariable a = (IVariable) col.getName(1).resolveBinding();
		IFunction g = (IFunction) col.getName(3).resolveBinding();
		assertNotNull(a);
		assertNotNull(g);
		assertTrue(g instanceof ICExternalBinding);
		
		assertEquals(col.size(), 11);
		assertInstances(col, a, 7);
		assertInstances(col, g, 3);
	}
	
	// typedef struct { int x; int y; } Coord;  
	// int f() {                               
	//    Coord xy = { .x = 10, .y = 11 };     
	// }      
	public void testFieldDesignators() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 9);
		IField x = (IField) col.getName(1).resolveBinding();
		IField y = (IField) col.getName(2).resolveBinding();
		ITypedef Coord = (ITypedef) col.getName(3).resolveBinding();
		
		assertInstances(col, x, 2);
		assertInstances(col, y, 2);
		assertInstances(col, Coord, 2);
	}
	
	// enum { member_one, member_two };
	// const char *nm[] = {
	//    [member_one] = "one",
	//    [member_two] = "two"
	// };
	public void testArrayDesignator() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 6);
		IEnumerator one = (IEnumerator) col.getName(1).resolveBinding();
		IEnumerator two = (IEnumerator) col.getName(2).resolveBinding();
		
		assertInstances(col, one, 2);
		assertInstances(col, two, 2);
	}
	
	// void f() {
	// if( a == 0 )
	// g( a );
	// else if( a < 0 )
	// g( a >> 1 );
	// else if( a > 0 )
	// g( *(&a + 2) );
	// }
	public void testBug83737() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		IASTIfStatement if_statement = (IASTIfStatement) ((IASTCompoundStatement) ((IASTFunctionDefinition) tu
				.getDeclarations()[0]).getBody()).getStatements()[0];
		assertEquals(((IASTBinaryExpression) if_statement
				.getConditionExpression()).getOperator(),
				IASTBinaryExpression.op_equals);
		IASTIfStatement second_if_statement = (IASTIfStatement) if_statement
		.getElseClause();
		assertEquals(((IASTBinaryExpression) second_if_statement
				.getConditionExpression()).getOperator(),
				IASTBinaryExpression.op_lessThan);
		IASTIfStatement third_if_statement = (IASTIfStatement) second_if_statement
		.getElseClause();
		assertEquals(((IASTBinaryExpression) third_if_statement
				.getConditionExpression()).getOperator(),
				IASTBinaryExpression.op_greaterThan);
	}
	
	// void f() {                    
	//    while(1){                  
	//       if( 1 ) goto end;       
	//    }                          
	//    end: ;                     
	// }    
	public void testBug84090_LabelReferences() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 3);
		ILabel end = (ILabel) col.getName(1).resolveBinding();
		
		IASTName[] refs = tu.getReferences(end);
		assertEquals(refs.length, 1);
		assertSame(refs[0].resolveBinding(), end);
	}
	
	// enum col { red, blue };
	// enum col c;
	public void testBug84092_EnumReferences() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector collector = new CNameCollector();
		tu.accept(collector);
		
		assertEquals(collector.size(), 5);
		IEnumeration col = (IEnumeration) collector.getName(0).resolveBinding();
		
		IASTName[] refs = tu.getReferences(col);
		assertEquals(refs.length, 1);
		assertSame(refs[0].resolveBinding(), col);
	}
	
	public void testBug84096_FieldDesignatorRef() throws Exception {
		IASTTranslationUnit tu = parse(
				"struct s { int a; } ss = { .a = 1 }; \n", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector collector = new CNameCollector();
		tu.accept(collector);
		
		assertEquals(collector.size(), 4);
		IField a = (IField) collector.getName(1).resolveBinding();
		
		IASTName[] refs = tu.getReferences(a);
		assertEquals(refs.length, 1);
		assertSame(refs[0].resolveBinding(), a);
	}
	
	public void testProblems() throws Exception {
		
		IASTTranslationUnit tu = parse(
				"    a += ;", ParserLanguage.C, true, false); //$NON-NLS-1$
		IASTProblem[] ps = CVisitor.getProblems(tu);
		assertEquals(ps.length, 1);
		ps[0].getMessage();
	}
	
	// enum e;
	// enum e{ one };
	public void testEnumerationForwards() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 3);
		IEnumeration e = (IEnumeration) col.getName(0).resolveBinding();
		IEnumerator[] etors = e.getEnumerators();
		assertTrue(etors.length == 1);
		assertFalse(etors[0] instanceof IProblemBinding);
		
		assertInstances(col, e, 2);
	}
	
	// void f() {                 
	//    int ( *p ) [2];         
	//    (&p)[0] = 1;            
	// }     
	public void testBug84185() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 3);
		IVariable p = (IVariable) col.getName(1).resolveBinding();
		assertTrue(p.getType() instanceof IPointerType);
		assertTrue(((IPointerType) p.getType()).getType() instanceof IArrayType);
		IArrayType at = (IArrayType) ((IPointerType) p.getType()).getType();
		assertTrue(at.getType() instanceof IBasicType);
		
		assertInstances(col, p, 2);
	}
	
	// void f() {                 
	//    int ( *p ) [2];         
	//    (&p)[0] = 1;            
	// }       
	public void testBug84185_2() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 3);
		
		IVariable p_ref = (IVariable) col.getName(2).resolveBinding();
		IVariable p_decl = (IVariable) col.getName(1).resolveBinding();
		
		assertSame(p_ref, p_decl);
	}
	
	// // example from: C99 6.5.2.5-16
	// struct s { int i; };
	// void f (void)
	// {
	// 		 struct s *p = 0, *q;
	// int j = 0;
	// q = p;
	// p = &((struct s){ j++ }); 
	// }
	public void testBug84176() throws Exception {
		parse(getAboveComment(), ParserLanguage.C, false, true);
	}
	
	// struct s { double i; } f(void);  
	// struct s f(void){}    
	public void testBug84266() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 7);
		
		ICompositeType s_ref = (ICompositeType) col.getName(4).resolveBinding();
		ICompositeType s_decl = (ICompositeType) col.getName(0)
		.resolveBinding();
		
		assertSame(s_ref, s_decl);
		CVisitor.clearBindings(tu);
		
		s_decl = (ICompositeType) col.getName(0).resolveBinding();
		s_ref = (ICompositeType) col.getName(4).resolveBinding();
		
		assertSame(s_ref, s_decl);
	}
	
	public void testBug84266_2() throws Exception {
		IASTTranslationUnit tu = parse("struct s f(void);", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 3);
		
		ICompositeType s = (ICompositeType) col.getName(0).resolveBinding();
		assertNotNull(s);
		
		tu = parse("struct s f(void){}", ParserLanguage.C); //$NON-NLS-1$
		col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 3);
		
		s = (ICompositeType) col.getName(0).resolveBinding();
		assertNotNull(s);
	}
	
	public void testBug84250() throws Exception {
		assertTrue(((IASTDeclarationStatement) ((IASTCompoundStatement) ((IASTFunctionDefinition) parse(
				"void f() { int (*p) [2]; }", ParserLanguage.C).getDeclarations()[0]).getBody()).getStatements()[0]).getDeclaration() instanceof IASTSimpleDeclaration); //$NON-NLS-1$
	}
	
	// struct s1 { struct s2 *s2p; /* ... */ }; // D1 
	// struct s2 { struct s1 *s1p; /* ... */ }; // D2 
	public void testBug84186() throws Exception {               
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 6);
		
		ICompositeType s_ref = (ICompositeType) col.getName(1).resolveBinding();
		ICompositeType s_decl = (ICompositeType) col.getName(3)
		.resolveBinding();
		
		assertSame(s_ref, s_decl);
		CVisitor.clearBindings(tu);
		
		s_decl = (ICompositeType) col.getName(3).resolveBinding();
		s_ref = (ICompositeType) col.getName(1).resolveBinding();
		
		assertSame(s_ref, s_decl);
	}
	
	// typedef struct { int a; } S;      
	// void g( S* (*funcp) (void) ) {    
	//    (*funcp)()->a;                 
	//    funcp()->a;                    
	// }    
	public void testBug84267() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 11);
		
		ITypedef S = (ITypedef) col.getName(2).resolveBinding();
		IField a = (IField) col.getName(10).resolveBinding();
		IParameter funcp = (IParameter) col.getName(7).resolveBinding();
		assertNotNull(funcp);
		assertInstances(col, funcp, 3);
		assertInstances(col, a, 3);
		
		assertTrue(funcp.getType() instanceof IPointerType);
		IType t = ((IPointerType) funcp.getType()).getType();
		assertTrue(t instanceof IFunctionType);
		IFunctionType ft = (IFunctionType) t;
		assertTrue(ft.getReturnType() instanceof IPointerType);
		assertSame(((IPointerType) ft.getReturnType()).getType(), S);
	}
	
	// void f( int m, int c[m][m] );        
	// void f( int m, int c[m][m] ){        
	//    int x;                            
	//    { int x = x; }                    
	// }   
	public void testBug84228() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertEquals(col.size(), 13);
		
		IParameter m = (IParameter) col.getName(3).resolveBinding();
		IVariable x3 = (IVariable) col.getName(12).resolveBinding();
		IVariable x2 = (IVariable) col.getName(11).resolveBinding();
		IVariable x1 = (IVariable) col.getName(10).resolveBinding();
		
		assertSame(x2, x3);
		assertNotSame(x1, x2);
		
		assertInstances(col, m, 6);
		assertInstances(col, x1, 1);
		assertInstances(col, x2, 2);
		
		IASTName[] ds = tu.getDeclarationsInAST(x2);
		assertEquals(ds.length, 1);
		assertSame(ds[0], col.getName(11));
	}
	
	public void testBug84236() throws Exception {
		String code = "double maximum(double a[ ][*]);"; //$NON-NLS-1$
		IASTSimpleDeclaration d = (IASTSimpleDeclaration) parse(code,
				ParserLanguage.C).getDeclarations()[0];
		IASTStandardFunctionDeclarator fd = (IASTStandardFunctionDeclarator) d
		.getDeclarators()[0];
		IASTParameterDeclaration p = fd.getParameters()[0];
		IASTArrayDeclarator a = (IASTArrayDeclarator) p.getDeclarator();
		ICASTArrayModifier star = (ICASTArrayModifier) a.getArrayModifiers()[1];
		assertTrue(star.isVariableSized());
		
	}
	
	// typedef int B;
	// void g() {
	// B * bp;  //1
	// }
	public void testBug85049() throws Exception {
		IASTTranslationUnit t = parse(getAboveComment(), ParserLanguage.C);
		IASTFunctionDefinition g = (IASTFunctionDefinition) t.getDeclarations()[1];
		IASTCompoundStatement body = (IASTCompoundStatement) g.getBody();
		final IASTStatement statement = body.getStatements()[0];
		assertTrue(statement instanceof IASTDeclarationStatement);
		IASTSimpleDeclaration bp = (IASTSimpleDeclaration) ((IASTDeclarationStatement) statement)
		.getDeclaration();
		assertTrue(bp.getDeclarators()[0].getName().resolveBinding() instanceof IVariable);
		
	}
	
	public void testBug86766() throws Exception {
		IASTTranslationUnit tu = parse(
				"char foo; void foo(){}", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IVariable foo = (IVariable) col.getName(0).resolveBinding();
		IProblemBinding prob = (IProblemBinding) col.getName(1)
		.resolveBinding();
		assertEquals(prob.getID(), IProblemBinding.SEMANTIC_INVALID_OVERLOAD);
		assertNotNull(foo);
	}
	
	public void testBug88338_C() throws Exception {
		IASTTranslationUnit tu = parse(
				"struct A; struct A* a;", ParserLanguage.C); //$NON-NLS-1$
		CPPNameCollector col = new CPPNameCollector();
		tu.accept(col);
		
		assertTrue(col.getName(0).isDeclaration());
		assertFalse(col.getName(0).isReference());
		assertTrue(col.getName(1).isReference());
		assertFalse(col.getName(1).isDeclaration());
		
		tu = parse("struct A* a; struct A;", ParserLanguage.C); //$NON-NLS-1$
		col = new CPPNameCollector();
		tu.accept(col);
		
		col.getName(2).resolveBinding();
		
		assertTrue(col.getName(0).isDeclaration());
		assertFalse(col.getName(0).isReference());
		
		assertTrue(col.getName(2).isDeclaration());
		assertFalse(col.getName(2).isReference());
	}
	
	public void test88460() throws Exception {
		IASTTranslationUnit tu = parse("void f();", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IFunction f = (IFunction) col.getName(0).resolveBinding();
		assertFalse(f.isStatic());
	}
	
	public void testBug90253() throws Exception {
		IASTTranslationUnit tu = parse(
				"void f(int par) { int v1; };", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IFunction f = (IFunction) col.getName(0).resolveBinding();
		IParameter p = (IParameter) col.getName(1).resolveBinding();
		IVariable v1 = (IVariable) col.getName(2).resolveBinding();
		
		IScope scope = f.getFunctionScope();
		
		IBinding[] bs = scope.find("par"); //$NON-NLS-1$
		assertEquals(bs.length, 1);
		assertSame(bs[0], p);
		
		bs = scope.find("v1"); //$NON-NLS-1$
		assertEquals(bs.length, 1);
		assertSame(bs[0], v1);
	}
	
	// struct S {};                
	// int S;                      
	// void f( ) {                 
	//    int S;                   
	//    {                        
	//       S :  ;                
	//    }                        
	// }                           
	public void testFind() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		ICompositeType S1 = (ICompositeType) col.getName(0).resolveBinding();
		IVariable S2 = (IVariable) col.getName(1).resolveBinding();
		IFunction f = (IFunction) col.getName(2).resolveBinding();
		IVariable S3 = (IVariable) col.getName(3).resolveBinding();
		ILabel S4 = (ILabel) col.getName(4).resolveBinding();
		
		IScope scope = f.getFunctionScope();
		
		IBinding[] bs = scope.find("S"); //$NON-NLS-1$
		
		assertNotNull(S2);
		assertEquals(bs.length, 3);
		assertSame(bs[0], S3);
		assertSame(bs[1], S1);
		assertSame(bs[2], S4);
	}
	
	public void test92791() throws Exception {
		IASTTranslationUnit tu = parse(
				"void f() { int x, y; x * y; }", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		for (int i = 0; i < col.size(); ++i)
			assertFalse(col.getName(i).resolveBinding() instanceof IProblemBinding);
		
		tu = parse(
				"int y; void f() { typedef int x; x * y; }", ParserLanguage.C); //$NON-NLS-1$
		col = new CNameCollector();
		tu.accept(col);
		for (int i = 0; i < col.size(); ++i)
			assertFalse(col.getName(i).resolveBinding() instanceof IProblemBinding);
		
	}
	
	public void testBug85786() throws Exception {
		IASTTranslationUnit tu = parse(
				"void f( int ); void foo () { void * p = &f; ( (void (*) (int)) p ) ( 1 ); }", ParserLanguage.C); //$NON-NLS-1$
		CNameCollector nameResolver = new CNameCollector();
		tu.accept(nameResolver);
		assertNoProblemBindings(nameResolver);
	}
	
	// void func() {
	//     int i=0;
	// i= i&0x00ff;
	// i= (i)&0x00ff;
	// }
	public void testBug95720() throws Exception {
		IASTTranslationUnit tu = parse( getAboveComment(), ParserLanguage.C );
		CNameCollector nameResolver = new CNameCollector();
		tu.accept(nameResolver);
		assertNoProblemBindings(nameResolver);        
	}
	
	// #define ONE(a, ...) int x
	// #define TWO(b, args...) int y
	// int main()
	// {
	// ONE("string"); /* err */
	// TWO("string"); /* err */
	// return 0;	
	// }
	public void testBug94365() throws Exception {
		parse(getAboveComment(), ParserLanguage.C);
	}
	
	// #define MACRO(a)
	// void main() {
	// MACRO('"');
	// }
	public void testBug95119_a() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		IASTDeclaration[] declarations = tu.getDeclarations();
		assertEquals(declarations.length, 1);
		assertNotNull(declarations[0]);
		assertTrue(declarations[0] instanceof IASTFunctionDefinition);
		assertEquals(((IASTFunctionDefinition) declarations[0]).getDeclarator()
				.getName().toString(), "main"); //$NON-NLS-1$
		assertTrue(((IASTCompoundStatement) ((IASTFunctionDefinition) declarations[0])
				.getBody()).getStatements()[0] instanceof IASTNullStatement);
	}
	
	// #define MACRO(a)
	// void main() {
	// MACRO('X');
	// }
	public void testBug95119_b() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C);
		IASTDeclaration[] declarations = tu.getDeclarations();
		assertEquals(declarations.length, 1);
		assertNotNull(declarations[0]);
		assertTrue(declarations[0] instanceof IASTFunctionDefinition);
		assertEquals(((IASTFunctionDefinition) declarations[0]).getDeclarator()
				.getName().toString(), "main"); //$NON-NLS-1$
		assertTrue(((IASTCompoundStatement) ((IASTFunctionDefinition) declarations[0])
				.getBody()).getStatements()[0] instanceof IASTNullStatement);
	}
	
	// typedef long _TYPE;
	// typedef _TYPE TYPE;
	// int function(TYPE (* pfv)(int parm));
	public void testBug81739() throws Exception {
		parse(getAboveComment(), ParserLanguage.C);
	}
	
	// float _Complex x;
	// double _Complex y;
	public void testBug95757() throws Exception {
		IASTTranslationUnit tu = parse( getAboveComment(), ParserLanguage.C, true, true );
		IASTDeclaration[] decls = tu.getDeclarations();
		
		assertTrue(((ICASTSimpleDeclSpecifier)((IASTSimpleDeclaration)decls[0]).getDeclSpecifier()).isComplex());
		assertEquals(((ICASTSimpleDeclSpecifier)((IASTSimpleDeclaration)decls[0]).getDeclSpecifier()).getType(), IASTSimpleDeclSpecifier.t_float);
		assertTrue(((ICASTSimpleDeclSpecifier)((IASTSimpleDeclaration)decls[1]).getDeclSpecifier()).isComplex());
		assertEquals(((ICASTSimpleDeclSpecifier)((IASTSimpleDeclaration)decls[1]).getDeclSpecifier()).getType(), IASTSimpleDeclSpecifier.t_double);
	}
	
	// int foo();                 
	// typeof({ int x = foo();    
	//          x; }) zoot;    
	public void testBug93980() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C, true);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IFunction foo = (IFunction) col.getName(0).resolveBinding();
		assertSame( foo, col.getName(2).resolveBinding() );
		
		IVariable zoot = (IVariable) col.getName(4).resolveBinding();
		IType t = zoot.getType();
		assertTrue( t instanceof IBasicType );
		assertEquals( ((IBasicType)t).getType(), IBasicType.t_int );
	}
	
	public void testBug95866() throws Exception {
		IASTTranslationUnit tu = parse( "int test[10] = { [0 ... 9] = 2 };", ParserLanguage.C, true, true ); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		assertNoProblemBindings(col);
	}
	
	public void testBug98502() throws Exception {
		IASTTranslationUnit tu = parse("typedef enum { ONE } e;", ParserLanguage.C, true, true ); //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IEnumeration etion = (IEnumeration) col.getName(0).resolveBinding();
		ITypedef e = (ITypedef) col.getName(2).resolveBinding();
		assertSame( e.getType(), etion );
	}
	
	// typedef struct _loop_data {   
	//    enum { PIPERR } pipe_err;  
	// } loop_data;                  
	// void f(){                     
	//    PIPERR;                    
	// }       
	public void testBug98365() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C, true);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IEnumerator etor = (IEnumerator) col.getName(2).resolveBinding();
		assertSame( etor, col.getName(6).resolveBinding() );
	}
	
	public void testBug99262() throws Exception {
		parse("void foo() {void *f; f=__null;}", ParserLanguage.C, true, true ); //$NON-NLS-1$
	}
	
	// int foo2(void *) {
	// return 0;
	// }
	// int foo3() {
	// return foo2(__null);
	// }
	public void testBug99262B() throws Exception {    	  
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C, true, true );
		assertTrue(((IASTIdExpression)((IASTFunctionCallExpression)((IASTReturnStatement)((IASTCompoundStatement)((IASTFunctionDefinition)tu.getDeclarations()[1]).getBody()).getStatements()[0]).getReturnValue()).getFunctionNameExpression()).getName().resolveBinding() instanceof IFunction);
	}
	
	// void f() {                    
	//    int a;                     
	//    { a; int a; }              
	// }   
	public void testBug98960() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C, true);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IVariable a1 = (IVariable) col.getName(1).resolveBinding();
		IVariable a2 = (IVariable) col.getName(2).resolveBinding();
		IVariable a3 = (IVariable) col.getName(3).resolveBinding();
		
		assertSame( a1, a2 );
		assertNotSame( a2, a3 );
	}
	
	public void testBug100408() throws Exception {
		IASTTranslationUnit tu = parse( "int foo() { int x=1; (x)*3; }", ParserLanguage.C );  //$NON-NLS-1$
		CNameCollector col = new CNameCollector();
		tu.accept(  col );
		assertNoProblemBindings( col );
	}
	
	// struct nfa;                   
	// void f() {                    
	//    struct nfa * n;            
	//    freenfa( n );              
	// }                             
	// static void freenfa( nfa )    
	// struct nfa * nfa;             
	// {                             
	// }     
	public void testBug98760() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C, true);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IFunction free = (IFunction) col.getName(4).resolveBinding();
		IParameter [] ps = free.getParameters();
		assertEquals( ps.length, 1 );
		
		assertSame( free, col.getName(6).resolveBinding() );
	}
	
	// void testCasting() {
	// typedef struct {
	//     int employee_id;
	// int dept_id;
	// } Employee;
	// #define MY_DETAILS { 20, 30 }
	// Employee e = (Employee)MY_DETAILS; 
	// }
	public void testBug79650() throws Exception {
		parseAndCheckBindings( getAboveComment() );
	}
	
	public void testBug80171() throws Exception {
		parseAndCheckBindings( "static var;"); //$NON-NLS-1$
	}
	
	// enum E_OPTIONCODE {
	//     red = 1,
	// black = 2,
	// };
	// void arithConversionTest(enum E_OPTIONCODE eOption)
	// {
	// int myColor = 5;
	// int temp = eOption - myColor; /* Syntax error */
	// if(eOption-myColor) /* Invalid arithmetic conversion */
	// {
	// }
	// }
	public void testBug79067() throws Exception {
		parseAndCheckBindings( getAboveComment() );
	}
	
	// enum COLOR {
	// RED=1
	// };
	// enum COLOR getColor() {
	// enum COLOR ret;
	// return ret;
	// }
	public void testBug84759() throws Exception {
		IASTTranslationUnit tu = parseAndCheckBindings( getAboveComment() );
		IASTFunctionDefinition fd = (IASTFunctionDefinition) tu.getDeclarations()[1];
		assertEquals( fd.getDeclSpecifier().getRawSignature(), "enum COLOR"); //$NON-NLS-1$
		
	}
	
	// int f() {
	// int x = 4;  while( x < 10 ) blah: ++x;
	// }
	public void test1043290() throws Exception {
		IASTTranslationUnit tu = parseAndCheckBindings(getAboveComment() );
		IASTFunctionDefinition fd = (IASTFunctionDefinition) tu.getDeclarations()[0];
		IASTStatement [] statements = ((IASTCompoundStatement)fd.getBody()).getStatements();
		IASTWhileStatement whileStmt = (IASTWhileStatement) statements[1];
		IASTLabelStatement labelStmt = (IASTLabelStatement) whileStmt.getBody();
		assertTrue( labelStmt.getNestedStatement() instanceof IASTExpressionStatement );
		IASTExpressionStatement es = (IASTExpressionStatement) labelStmt.getNestedStatement();
		assertTrue( es.getExpression() instanceof IASTUnaryExpression );
	}
	
	// void f() {                         
	//    int x;                          
	//    for( int x; ; )                 
	//       blah: x;                     
	// }        
	public void testBug104390_2() throws Exception {
		IASTTranslationUnit tu = parse(getAboveComment(), ParserLanguage.C, true);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		IVariable x = (IVariable) col.getName(1).resolveBinding();
		IVariable x2 = (IVariable) col.getName(2).resolveBinding();
		assertNotSame( x, x2 );
		assertSame( x2, col.getName(4).resolveBinding() );
		assertTrue( col.getName(3).resolveBinding() instanceof ILabel );
	}
	
	// int f() { 
	// int i;
	// do { ++i; } while( i < 10 );
	// return 0;
	// }
	public void testBug104800() throws Exception {
		IASTTranslationUnit tu = parseAndCheckBindings( getAboveComment() );
		IASTFunctionDefinition f = (IASTFunctionDefinition) tu.getDeclarations()[0];
		IASTCompoundStatement body = (IASTCompoundStatement) f.getBody();
		assertEquals( body.getStatements().length, 3 );
	}
	
	public void testBug107150() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("#define FUNC_PROTOTYPE_PARAMS(list)    list\r\n"); //$NON-NLS-1$
		buffer.append("int func1 FUNC_PROTOTYPE_PARAMS((int arg1)){\r\n"); //$NON-NLS-1$
		buffer.append("return 0;\r\n"); //$NON-NLS-1$
		buffer.append("}\r\n"); //$NON-NLS-1$
		buffer.append("int func2 FUNC_PROTOTYPE_PARAMS\r\n"); //$NON-NLS-1$
		buffer.append("((int arg1)){\r\n"); //$NON-NLS-1$
		buffer.append("return 0;\r\n"); //$NON-NLS-1$
		buffer.append("}\r\n"); //$NON-NLS-1$
		IASTTranslationUnit tu = parse(buffer.toString(), ParserLanguage.C);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
		tu = parse(buffer.toString(), ParserLanguage.CPP);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
		
		buffer = new StringBuffer();
		buffer.append("#define FUNC_PROTOTYPE_PARAMS(list)    list\n"); //$NON-NLS-1$
		buffer.append("int func1 FUNC_PROTOTYPE_PARAMS((int arg1)){\n"); //$NON-NLS-1$
		buffer.append("return 0;\n"); //$NON-NLS-1$
		buffer.append("}\n"); //$NON-NLS-1$
		buffer.append("int func2 FUNC_PROTOTYPE_PARAMS\n"); //$NON-NLS-1$
		buffer.append("((int arg1)){\n"); //$NON-NLS-1$
		buffer.append("return 0;\n"); //$NON-NLS-1$
		buffer.append("}\n"); //$NON-NLS-1$
		tu= parse(buffer.toString(), ParserLanguage.C);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
		tu= parse(buffer.toString(), ParserLanguage.CPP);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
	}
	
	public void testBug107150b() throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("#define FUNC_PROTOTYPE_PARAMS(list)    list\r\n"); //$NON-NLS-1$
		buffer.append("int func1 FUNC_PROTOTYPE_PARAMS((int arg1)){\r\n"); //$NON-NLS-1$
		buffer.append("return 0;\r\n"); //$NON-NLS-1$
		buffer.append("}\r\n"); //$NON-NLS-1$
		buffer.append("int func2 FUNC_PROTOTYPE_PARAMS\r\n \r\n \t \r\n    \r\n "); //$NON-NLS-1$
		buffer.append("((int arg1)){\r\n"); //$NON-NLS-1$
		buffer.append("return 0;\r\n"); //$NON-NLS-1$
		buffer.append("}\r\n"); //$NON-NLS-1$
		IASTTranslationUnit tu= parse(buffer.toString(), ParserLanguage.C);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
		
		tu = parse(buffer.toString(), ParserLanguage.CPP);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
		
		buffer = new StringBuffer();
		buffer.append("#define FUNC_PROTOTYPE_PARAMS(list)    list\n"); //$NON-NLS-1$
		buffer.append("int func1 FUNC_PROTOTYPE_PARAMS((int arg1)){\n"); //$NON-NLS-1$
		buffer.append("return 0;\n"); //$NON-NLS-1$
		buffer.append("}\n"); //$NON-NLS-1$
		buffer.append("int func2 FUNC_PROTOTYPE_PARAMS\n"); //$NON-NLS-1$
		buffer.append("((int arg1)){\n"); //$NON-NLS-1$
		buffer.append("return 0;\n"); //$NON-NLS-1$
		buffer.append("}\n"); //$NON-NLS-1$
		tu = parse(buffer.toString(), ParserLanguage.C);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
		tu = parse(buffer.toString(), ParserLanguage.CPP);
		assertFalse( tu.getDeclarations()[1] instanceof IASTProblemDeclaration );
	}
	
	// NWindow NewWindowDuplicate(NWindow theWindow, bool insert)\n");
	// {
	//         NWindow newWindow;
	//                 newWindow = new GenericWindow();
	//     if (newWindow == NULL)
	//     return NULL;
	//     TwinWindowOF(theWindow) = newWindow;
	//     TwinWindowOF(newWindow) = NULL;
	//     ParentWindowOF(newWindow) = ParentWindowOF(theWindow);
	//     DGlobalsOF(newWindow) = DGlobalsOF(theWindow);
	//     HashMapOF(newWindow) = HashMapOF(theWindow);
	//     OwnerWindowOF(newWindow) = OwnerWindowOF(theWindow);
	//     ChildOF(newWindow) = ChildOF(theWindow);
	//         MakeNameOF(newWindow, NameOF(theWindow));
	//     KindOF(newWindow) = KindOF(theWindow);
	//     IsVisibleOF(newWindow) = IsVisibleOF(theWindow);
	//     FocusOF(newWindow) = FocusOF(theWindow);
	//     IsFloating(newWindow) = IsFloating(theWindow);
	//     HasCloseBox(newWindow) = HasCloseBox(theWindow);
	//     IsSearchNB(newWindow) = IsSearchNB(theWindow);
	//     IsFBWindow(newWindow) = FALSE;
	//     ShellOF(newWindow) = ShellOF(theWindow);
	//     DrawOnOF(newWindow) = DrawOnOF(theWindow);
	//     IsBusyOF(newWindow) = IsBusyOF(theWindow);
	//     InvalRgnOF(newWindow) = XCreateRegion();
	//     IdleOF(newWindow) = IdleOF(theWindow);
	//     ShellPainterOF(newWindow)  = ShellPainterOF(theWindow);
	//     CanvasPainterOF(newWindow) = CanvasPainterOF(theWindow);
	//     StatusPainterOF(newWindow) = StatusPainterOF(theWindow);
	//     NotebookOF(newWindow) = NotebookOF(theWindow);
	//     PopupWindowOF(newWindow) = PopupWindowOF(theWindow);
	//     IC_IsFromIM(theWindow) = FALSE;
	//     IsDestroyPendingOF(newWindow) = FALSE;
	//     if (IsNotebookWindow(newWindow))
	//             DockedWindowOF(newWindow) = NewDockedWindow(newWindow, false);
	//     else
	//         DockedWindowOF(newWindow) = NULL;
	//     if (insert)
	//         _addToListHead(newWindow);
	//     return newWindow;
	// }
	public void testBug143502() throws Exception {
		parse(getAboveComment(), ParserLanguage.C, true, false);
	}
	
	// void func(int a) {
	//    int z=0;
	//    z= (a)+z;
	//    z= (a)-z;
	//    z= (a)*z;
	//    z= (a)&z;
	//    z= (a)|z;
	//    z= (a)/z;
	//    z= (a)%z;
	// }
	public void testBracketAroundIdentifier_168924() throws IOException, ParserException {
		String content= getAboveComment();
		IASTTranslationUnit tu= parse(content, ParserLanguage.C, true, true);
		IASTFunctionDefinition func= (IASTFunctionDefinition) tu.getDeclarations()[0];
		IASTParameterDeclaration[] params= ((IASTStandardFunctionDeclarator) func.getDeclarator()).getParameters();
		IBinding binding= params[0].getDeclarator().getName().resolveBinding();
		assertEquals(7, tu.getReferences(binding).length);
		
		tu= parse(content, ParserLanguage.CPP, true, true);
		func= (IASTFunctionDefinition) tu.getDeclarations()[0];
		params= ((IASTStandardFunctionDeclarator) func.getDeclarator()).getParameters();
		binding= params[0].getDeclarator().getName().resolveBinding();
		assertEquals(7, tu.getReferences(binding).length);
	}
	
	//  #define MAC(x) x
	//  void func() {
	//     MAC(");
	//  }
	public void testBug179383() throws ParserException, IOException {
		parse(getAboveComment(), ParserLanguage.C, false, false);
	}
	
	/**
	 * Bug in not removing single-line comments from macros.
	 * @throws Exception
	 */
	public void testMacroCommentsBug_177154() throws Exception {
		// simple case
		String simple = 
			"#define LIT 1  // my value\r\n" + 
			"int func(int x) {\r\n" + 
			"}\r\n" + 
			"int main() {\r\n" + 
			"  return func(LIT);   // fails to parse\r\n" + 
			"}\r\n"; 
		
		IASTTranslationUnit tu = parse( simple, ParserLanguage.CPP, true, true );
		
		// actual reduced test case, plus extra cases
		String text =
			"#define KBOOT		1 //0x00000002\r\n" + 
			"#define KBOOT2	 /* value */	1  /* another */ //0x00000002\r\n" + 
			"#define KBOOT3	 /* value \r\n" +
			" multi line\r\n"+
			" comment */	1  \\\r\n"+
			"/* another */ + \\\r\n"+
			"2 //0x00000002\r\n" + 
			"#define DEBUGNUM(x) (KDebugNum(x))\r\n" + 
			"bool KDebugNum(int aBitNum);\r\n" + 
			"#define __KTRACE_OPT(a,p) {if((DEBUGNUM(a)))p;}\r\n" +
			"void fail();\r\n"+
			"void test() {\r\n"+
			"__KTRACE_OPT(KBOOT,fail());\r\n" + 
			"__KTRACE_OPT(KBOOT2,fail());\r\n" + 
			"}\r\n"
			;
		
		// essential test: this code should be parseable
		tu = parse( text, ParserLanguage.CPP, true, true );
		
		// verify macros
		IASTPreprocessorMacroDefinition[] macroDefinitions = tu.getMacroDefinitions();
		assertEquals(5, macroDefinitions.length);
		assertEquals("1", macroDefinitions[0].getExpansion());
		assertEquals("1", macroDefinitions[1].getExpansion());
		// regression test for #64268 and #71733 which also handle comments
		String expectExpansion= "1 + 2";
		assertEquals(expectExpansion, macroDefinitions[2].getExpansion());
		assertEquals("(KDebugNum(x))", macroDefinitions[3].getExpansion());
		assertEquals("{if((DEBUGNUM(a)))p;}", macroDefinitions[4].getExpansion());
		
		// TODO: exhaustive macro testing
	}
	
	// void (decl)(char);
	// void foo() {
	//    decl('a');
	// }
	public void testBug181305_1() throws Exception {
		for(ParserLanguage lang : ParserLanguage.values()) {
			IASTTranslationUnit tu = parse( getAboveComment(), lang, true, true );
			
			// check class
			IASTFunctionDefinition fd = (IASTFunctionDefinition) tu.getDeclarations()[1];
			IASTCompoundStatement comp_stmt= (IASTCompoundStatement) fd.getBody();
			IASTExpressionStatement expr_stmt= (IASTExpressionStatement) comp_stmt.getStatements()[0];
			IASTFunctionCallExpression expr= (IASTFunctionCallExpression) expr_stmt.getExpression();
			IASTIdExpression idExpr= (IASTIdExpression) expr.getFunctionNameExpression();
			IBinding binding= idExpr.getName().resolveBinding();
			assertTrue(lang.toString(),  binding instanceof IFunction);
			assertFalse(lang.toString(), binding instanceof IProblemBinding);
			assertEquals(binding.getName(), "decl");
		}
	}
	
	// void (*decl)(char);
	// void foo() {
	//    decl('a');
	// }
	public void testBug181305_2() throws Exception {
		for(ParserLanguage lang : ParserLanguage.values()) {
			IASTTranslationUnit tu = parse( getAboveComment(), lang, true, true );
			
			// check class
			IASTFunctionDefinition fd = (IASTFunctionDefinition) tu.getDeclarations()[1];
			IASTCompoundStatement comp_stmt= (IASTCompoundStatement) fd.getBody();
			IASTExpressionStatement expr_stmt= (IASTExpressionStatement) comp_stmt.getStatements()[0];
			IASTFunctionCallExpression expr= (IASTFunctionCallExpression) expr_stmt.getExpression();
			IASTIdExpression idExpr= (IASTIdExpression) expr.getFunctionNameExpression();
			IBinding binding= idExpr.getName().resolveBinding();
			assertTrue(lang.toString(),  binding instanceof IVariable);
			assertFalse(lang.toString(), binding instanceof IProblemBinding);
		}
	}
	
	public void testBug181735() throws Exception {
		String code=
			"int (*f)(int);\n"
			+	"int g(int n) {return n;}\n"
			+   "int g(int n, int m) {return n;}\n"
			+   "void foo() { f=g; }";
		
		for(ParserLanguage lang : ParserLanguage.values())
			parseAndCheckBindings(code, lang);
	}
	
	// void test() {
	//    char d= *"b";
	// }
	public void testBug181942() throws Exception {
		for(ParserLanguage lang : ParserLanguage.values()) 
			parse( getAboveComment(), lang, true, true );
	}
	
	public void testMacroCommentsBug_177154_2() throws Exception {
		String noCommentMacro =
			"#define Sonar16G(x) ((Sonr16G(x)<<16)|0xff000000L)\r\n"; 
		String commentMacro = 	
			"#define Sonar16G(x) ((Sonr16G(x)<<16)|0xff000000L)	// add the varf value\r\n"; 
		
		String textTail = "\r\n" + 
		"const int snd16SonarR[32] = {\r\n" + 
		"		0xFF000000L,   Sonar16G(0x01), Sonar16G(0x02), Sonar16G(0x03),\r\n" + 
		"		Sonar16G(0x04), Sonar16G(0x05), Sonar16G(0x06), Sonar16G(0x07),\r\n" + 
		"		Sonar16G(0x08), Sonar16G(0x09), Sonar16G(0x0A), Sonar16G(0x0B),\r\n" + 
		"		Sonar16G(0x0C), Sonar16G(0x0D), Sonar16G(0x0E), Sonar16G(0x0F),\r\n" + 
		"		Sonar16G(0x10), Sonar16G(0x11), Sonar16G(0x12), Sonar16G(0x13),\r\n" + 
		"		Sonar16G(0x14), Sonar16G(0x15), Sonar16G(0x16), Sonar16G(0x17),\r\n" + 
		"		Sonar16G(0x18), Sonar16G(0x19), Sonar16G(0x1A), Sonar16G(0x1B),\r\n" + 
		"		Sonar16G(0x1C), Sonar16G(0x1D), Sonar16G(0x1E), Sonar16G(0x1F),\r\n" + 
		"	};\r\n" + 
		"\r\n" + 
		"";
		
		// this should work
		String textNoComment = noCommentMacro + textTail;
		IASTTranslationUnit tu = parse( textNoComment, ParserLanguage.CPP, true, true );
		
		// this fails
		String textComment = commentMacro + textTail;
		tu = parse( textComment, ParserLanguage.CPP, true, true );
		
	}
	
	// int __builtin_sin;
	public void testBug182464() throws Exception {
		for(ParserLanguage lang : ParserLanguage.values())
			parseAndCheckBindings( getAboveComment(), lang, true);
	}
	
	public void testBug186018() throws Exception {
		String code = 
			"int main() { \n" +
			"    switch(1) { \n" +
			"        case 1 : \n" +
			"        case 2 : \n" +
			"            printf(\"pantera rules\"); \n" +
			"    } \n" +
			"}\n";
		
		parseAndCheckBindings(code, ParserLanguage.C);
	}
	
	//	template <typename T>
	//	class auto_ptr {
	//	private:
	//	  T* ptr;
	//	public:
	//	  explicit auto_ptr(T* p = 0) : ptr(p) {}
	//	  ~auto_ptr()                 { delete ptr; }
	//	  T& operator*()              { return *ptr; }
	//	  T* operator->()             { return ptr; }
	//	};
	//
	//	class A {
	//	public:
	//	  int method() { return 0; }
	//	};
	//
	//	class B {
	//	  auto_ptr<A> a;
	//
	//	  void f() {
	//	    int b = a->method();        // Problem: method
	//	    int c = (*a).method();      // Problem: method
	//	  }
	//	};
	public void test186736() throws Exception {
		IASTTranslationUnit tu= parseAndCheckBindings(getAboveComment(), ParserLanguage.CPP);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		IBinding methodb= col.getName(27).resolveBinding();
		IBinding methodc= col.getName(30).resolveBinding();
		assertEquals("method", methodb.getName());
		assertEquals("method", methodc.getName());
		assertInstance(methodb, ICPPMethod.class);
		assertInstance(methodc, ICPPMethod.class);
		assertEquals("A", ((ICPPMethod)methodb).getClassOwner().getName());
		assertEquals("A", ((ICPPMethod)methodc).getClassOwner().getName());
	}
	
	//	template <typename T, typename U>
	//	class odd_pair {
	//	private:
	//	  T* ptr1;
	//	  U* ptr2;
	//	public:
	//	  T* operator->()             { return 0; }
	//	  U* operator->() const       { return 0; }
	//	};
	//
	//	class A {
	//	public:
	//	  int method() { return 0; }
	//	};
	//
	//	class AA {
	//	public:
	//	  int method() { return 0; }
	//	};
	//
	//	class B {
	//	public:
	//	  odd_pair<A, AA> a1;
	//	  const odd_pair<A, AA> a2;
	//
	//	  B() {
	//	  }
	//
	//	  void f() {
	//	    int b1 = a1->method();        // Problem: method
	//	    int b2 = a2->method();        // Problem: method
	//	  }
	//	};
	public void test186736_variant1() throws Exception {
		IASTTranslationUnit tu= parseAndCheckBindings(getAboveComment(), ParserLanguage.CPP);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		IBinding methodA= col.getName(30).resolveBinding();
		IBinding methodAA= col.getName(33).resolveBinding();
		assertEquals("method", methodA.getName());
		assertEquals("method", methodAA.getName());
		assertInstance(methodA, ICPPMethod.class);
		assertInstance(methodAA, ICPPMethod.class);
		assertEquals("A", ((ICPPMethod)methodA).getClassOwner().getName());
		assertEquals("AA", ((ICPPMethod)methodAA).getClassOwner().getName());
	}
	
	//	class B {
	//		public:
	//			void bar() {}
	//	};
	//	class A {
	//		public:
	//			B* operator->() { return new B(); }
	//			void foo() {}
	//	};
	//
	//	void main() {
	//		A a, &aa=a, *ap= new A();
	//		a.foo();
	//		ap->foo();
	//		aa.foo();
	//		(*ap)->bar();
	//		(&aa)->foo();
	//		(&a)->foo();
	//	}
	public void test186736_variant2() throws Exception {
		IASTTranslationUnit tu= parseAndCheckBindings(getAboveComment(), ParserLanguage.CPP);
	}
	
	// typedef int int32;
	// int32 f(int32 (*p)) {
	//   return *p;
	// }
	public void test167833() throws Exception {
		parseAndCheckBindings(getAboveComment(), ParserLanguage.CPP);
		parseAndCheckBindings(getAboveComment(), ParserLanguage.C);
	}	
	
	// // this is a \
	// single line comment 
	// char str[] = " multi \
	// line \
	// string";
	public void testBug188707_backslashNewline() throws Exception {
		parseAndCheckBindings( getAboveComment() );
	}
	
	// typedef A B;
	// typedef C D;
	// typedef E E;
	// typedef typeof(G) G;
	// typedef H *H;
	// typedef I *************I;
	// typedef int (*J)(J);
	public void testBug192165() throws Exception {
		for(ParserLanguage lang : ParserLanguage.values()) {
			IASTTranslationUnit tu = parse( getAboveComment(), lang, true, false );
			CNameCollector col = new CNameCollector();
			tu.accept(col);
			assertInstance(col.getName(0).resolveBinding(), IProblemBinding.class);
			assertInstance(col.getName(1).resolveBinding(), ITypedef.class);
			assertInstance(col.getName(2).resolveBinding(), IProblemBinding.class);
			assertInstance(col.getName(3).resolveBinding(), ITypedef.class);
			assertInstance(col.getName(4).resolveBinding(), IProblemBinding.class);
			assertInstance(col.getName(5).resolveBinding(), ITypedef.class);
			assertInstance(col.getName(6).resolveBinding(), IProblemBinding.class);
			assertInstance(col.getName(7).resolveBinding(), ITypedef.class);
			assertInstance(col.getName(8).resolveBinding(), IProblemBinding.class);
			assertInstance(col.getName(9).resolveBinding(), ITypedef.class);
			assertInstance(col.getName(10).resolveBinding(), IProblemBinding.class);
			assertInstance(col.getName(11).resolveBinding(), ITypedef.class);
			
			// function ptr
			final IBinding typedef = col.getName(12).resolveBinding();
			final IBinding secondJ = col.getName(13).resolveBinding();
			assertInstance(typedef, ITypedef.class);
			if (lang == ParserLanguage.CPP) {
				assertInstance(secondJ, IProblemBinding.class);
			} else {
				// for plain C this is actually not a problem, the second J has to be interpreted as a (useless) 
				// parameter name.
				assertInstance(typedef, ITypedef.class);
				isTypeEqual(((ITypedef) typedef).getType(), "int (int) *");
			}
		}
	}
	
	// /* a comment */
	// #define INVALID(a, b)  ## a ## b
	// INVALID(1, 2)
	//
	public void test192639() throws Exception {
		parse( getAboveComment(), ParserLanguage.CPP, false, false, true ); 
		parse( getAboveComment(), ParserLanguage.C, false, false, true ); 
	}
	
	public void test195943() throws Exception {
		final int depth= 100;
		StringBuffer buffer = new StringBuffer();
		buffer.append("#define M0 1\n");
		for (int i = 1; i < depth; i++) {
			buffer.append("#define M" + i + " (M" + (i-1) + "+1)\n");
		}
		buffer.append("int a= M" + (depth-1) + ";\n");
		long time= System.currentTimeMillis();
		parse(buffer.toString(), ParserLanguage.CPP); 
		parse( buffer.toString(), ParserLanguage.C);
		assertTrue(System.currentTimeMillis()-time < 2000);
	}
	
	// int array[12]= {};
	public void testBug196468_emptyArrayInitializer() throws Exception {
		final String content = getAboveComment();
		parse( content, ParserLanguage.CPP, false); 
		parse( content, ParserLanguage.CPP, true); 
		parse( content, ParserLanguage.C, true);
		try {
			parse( content, ParserLanguage.C, false);
			fail("C89 does not allow empty braces in array initializer");
		}
		catch (ParserException e) {
		}
	}
	
	//  #define foo(f,...)
	//	void bar(void){}
	//	int main(int argc, char **argv) {
	//	   foo("a", bar);        // ok
	//	   foo("a", bar, bar);   // ok
	//	   foo("a", bar());      // Eclipse Syntax error
	//	   foo("a", bar, bar()); // Eclipse Syntax error
	//	   foo("a", bar(), bar); // Eclipse Syntax error
	//     return 0;
	//  }
	public void testBug197633_parenthesisInVarargMacros() throws Exception {
		final String content = getAboveComment();
		parse( content, ParserLanguage.CPP); 
		parse( content, ParserLanguage.C); 
	}
	
	// void  ( __attribute__((__stdcall__))* foo1) (int);
	// void  ( * __attribute__((__stdcall__)) foo2) (int);
	// void  ( * __attribute__((__stdcall__))* foo3) (int);
	public void testBug191450_attributesInBetweenPointers() throws Exception {
		parse( getAboveComment(), ParserLanguage.CPP, true, true );
		parse( getAboveComment(), ParserLanguage.C, true, true );
	}
	
	// class NameClash {}; 
	// namespace NameClash {};
	// namespace NameClash2 {};
	// class NameClash2 {}; 
	public void testBug202271_nameClash() throws Exception {
		IASTTranslationUnit tu= parseAndCheckBindings( getAboveComment(), ParserLanguage.CPP, true );
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		assertInstance(col.getName(0).resolveBinding(), ICPPClassType.class);
		assertInstance(col.getName(1).resolveBinding(), ICPPNamespace.class);
		assertInstance(col.getName(2).resolveBinding(), ICPPNamespace.class);
		assertInstance(col.getName(3).resolveBinding(), ICPPClassType.class);
	}
	
	// #define WRAP(var) var
	// #define MACRO 1
	// int a= MACRO;
	// int b= WRAP(MACRO);
	public void testBug94673_refsForMacrosAsArguments() throws Exception {
		String content= getAboveComment();
		IASTTranslationUnit tu= parseAndCheckBindings( content, ParserLanguage.CPP, true );
		IASTPreprocessorMacroDefinition[] defs= tu.getMacroDefinitions();
		assertEquals(2, defs.length);
		IASTPreprocessorMacroDefinition md= defs[1];
		assertEquals("MACRO", md.getName().toString());
		IMacroBinding binding= (IMacroBinding) md.getName().resolveBinding();
		assertNotNull(binding);
		IASTName[] refs= tu.getReferences(binding);
		assertEquals(2, refs.length);
		IASTFileLocation loc= refs[1].getFileLocation();
		final int idx = content.indexOf("WRAP(MACRO)");
		assertEquals(idx, loc.getNodeOffset());
		IASTImageLocation iloc= refs[1].getImageLocation();
		assertEquals(idx+5, iloc.getNodeOffset());
	}
	
	//	void OSi_Panic(const char *file, int line) {};
	//	void OSi_Panic(const char *file, int line, const char *fmt, ...) {};
	//
	//	#define ASSERT(exp, args...)\
	//	{\
	//	    if (!(exp))\
	//	    {\
	//	        OSi_Panic(__FILE__, __LINE__, ##args);\
	//	    }\
	//	}\
	//
	//	int main()
	//	{
	//	    int a = 0;
	//	    int b = 0;
	//	    ASSERT(a > b, "Error: a=%d, b=%d", a, b);// marked with error
	//	    ASSERT(a > b, "Error!");// marked with error also
	//	    ASSERT(false);// fine
	//	}
	
	//	void OSi_Panic(const char *file, int line) {};
	//	void OSi_Panic(const char *file, int line, const char *fmt, ...) {};
	//
	//	#define ASSERT(exp, ...)\
	//	{\
	//	    if (!(exp))\
	//	    {\
	//	        OSi_Panic(__FILE__, __LINE__, ##__VA_ARGS__);\
	//	    }\
	//	}\
	//
	//	int main()
	//	{
	//	    int a = 0;
	//	    int b = 0;
	//	    ASSERT(a > b, "Error: a=%d, b=%d", a, b);// marked with error
	//	    ASSERT(a > b, "Error!");// marked with error also
	//	    ASSERT(false);// fine
	//	}
	public void testBug188855_gccExtensionForVariadicMacros() throws Exception {
		StringBuffer[] buffer = getContents(2);
		final String content1 = buffer[0].toString();
		final String content2 = buffer[1].toString();
		parse( content1, ParserLanguage.CPP); 
		parse( content1, ParserLanguage.C); 
		parse( content2, ParserLanguage.CPP); 
		parse( content2, ParserLanguage.C); 
	}
	
	
	// typedef struct Point {
	// 	int x;
	// 	int y;
	// } Point ;
	// 
	// typedef struct Tag {
	// 	int tag;
	// } Tag ;
	// 
	// typedef struct Line {
	// 	Point p1;
	// 	Point p2;
	// 	Tag t;
	// } Line ;
	//  
	// int foo() {
	// 	Point p1 = {.x = 1, .y = 2}; 
	// 	 
	// 	Line l1 = {.p1 = {.x = 1, .y = 2}, {.x = 1, .y = 2}, {.tag = 5}}; 
	// 	Line l2 = {.t.tag = 9, .p1.x = 1, .p2.x = 3, .p1.y = 4, .p2.y = 9};
	// 	
	// 	Point points[] = {{.x=1, .y=1}, {.x=2, .y=2}};
	// }   
	public void _testBug210019_nestedDesignatedInitializers() throws Exception {
		IASTTranslationUnit tu = parseAndCheckBindings(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		// Point p1        
		assertField(col.getName(18).resolveBinding(), "x", "Point");
		assertField(col.getName(19).resolveBinding(), "y", "Point");
		
		// Line l1
		assertField(col.getName(22).resolveBinding(), "p1", "Line");
		assertField(col.getName(23).resolveBinding(), "x", "Point");
		assertField(col.getName(24).resolveBinding(), "y", "Point");
		assertField(col.getName(25).resolveBinding(), "x", "Point");
		assertField(col.getName(26).resolveBinding(), "y", "Point");
		assertField(col.getName(27).resolveBinding(), "tag", "Tag");
		
		// Line l2
		assertField(col.getName(30).resolveBinding(), "t",  "Line");
		assertField(col.getName(31).resolveBinding(), "tag", "Tag");
		assertField(col.getName(32).resolveBinding(), "p1", "Line");
		assertField(col.getName(33).resolveBinding(), "x", "Point");
		assertField(col.getName(34).resolveBinding(), "p2", "Line");
		assertField(col.getName(35).resolveBinding(), "x", "Point");
		assertField(col.getName(36).resolveBinding(), "p1", "Line");
		assertField(col.getName(37).resolveBinding(), "y", "Point");
		assertField(col.getName(38).resolveBinding(), "p2", "Line");
		assertField(col.getName(39).resolveBinding(), "y", "Point");
		
		// Point points[]
		assertField(col.getName(42).resolveBinding(), "x", "Point");
		assertField(col.getName(43).resolveBinding(), "y", "Point");
		assertField(col.getName(44).resolveBinding(), "x", "Point");
		assertField(col.getName(45).resolveBinding(), "y", "Point");
	}
	
	
	// struct S1 { 
	//   int i; 
	//   float f;
	//   int a[2];
	// };
	// 
	// struct S1 x = {
	//   .f=3.1, 
	//   .i=2,
	//   .a[1]=9
	// };
	//  
	// struct S2 {
	//   int x, y;
	// };
	// 
	// struct S2 a1[3] = {1, 2, 3, 4, 5, 6};
	// 
	// struct S2 a2[3] =
	// {{1, 2},{3, 4}, 5, 6};
	// 
	// struct S2 a3[3] =
	// {
	//   [2].y=6, [2].x=5,
	//   [1].y=4, [1].x=3,
	//   [0].y=2, [0].x=1
	// };
	//   
	// struct S2 a4[3] =
	// {
	//   [0].x=1, [0].y=2,
	//   {.x=3, .y=4},
	//   5, [2].y=6
	// };
	// 
	// struct S2 a5[3] =
	// {
	//   [2].x=5, 6,
	//   [0].x=1, 2,
	//   3, 4
	// };
	public void testBug210019_designatedInitializers() throws Exception {
		IASTTranslationUnit tu = parseAndCheckBindings(getAboveComment(), ParserLanguage.C);
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		
		assertField(col.getName(6).resolveBinding(), "f", "S1");
		assertField(col.getName(7).resolveBinding(), "i", "S1");
		assertField(col.getName(8).resolveBinding(), "a", "S1");
		
		assertField(col.getName(18).resolveBinding(), "y", "S2");
		assertField(col.getName(19).resolveBinding(), "x", "S2");
		assertField(col.getName(20).resolveBinding(), "y", "S2");
		assertField(col.getName(21).resolveBinding(), "x", "S2");
		assertField(col.getName(22).resolveBinding(), "y", "S2");
		assertField(col.getName(23).resolveBinding(), "x", "S2");
		
		assertField(col.getName(26).resolveBinding(), "x", "S2");
		assertField(col.getName(27).resolveBinding(), "y", "S2");
		assertField(col.getName(28).resolveBinding(), "x", "S2");
		assertField(col.getName(29).resolveBinding(), "y", "S2");
		assertField(col.getName(30).resolveBinding(), "y", "S2");
		
		assertField(col.getName(33).resolveBinding(), "x", "S2");
		assertField(col.getName(34).resolveBinding(), "x", "S2");
	}
	
	// extern "C" {
	// extern "C" {
	//    void externFunc();
	// }
	// }
	// void test() {
	//    externFunc();
	// }
	public void testBug183126_nestedLinkageSpecs() throws Exception {
		parseAndCheckBindings(getAboveComment(), ParserLanguage.CPP);
	}
	
	// int* i= 0;
	// void f1(const int**);
	// void f2(int *const*);
	// void f3(const int *const*);
	//
	// void test() {
	//  f1(&i); // forbidden
	//  f2(&i); // ok
	//  f3(&i); // ok
	// }
	public void testBug213029_cvConversion() throws Exception {
		IASTTranslationUnit tu = parse( getAboveComment(), ParserLanguage.CPP, false ); 
		CNameCollector col = new CNameCollector();
		tu.accept(col);
		for(Object o : col.nameList) {
			IASTName n = (IASTName)o; 
			if (n.isReference() && "f1".equals(n.toString()))
				assertTrue(n.resolveBinding() instanceof IProblemBinding);
			else
				assertFalse(n.resolveBinding() instanceof IProblemBinding);
		}
	}
	
	// void isTrue( int field, int bit ){
	//    return ((field) & (bit));
	// }
	// void test() {
	//   int foux=0, bhar=0;
	//   foux = (foux) - bhar1;
	// }
	public void testBug100641_106279_castAmbiguity() throws Exception {
		boolean cpp= false;
		do {
			BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), cpp);
			ba.assertNonProblem("field)", 5);
			ba.assertNonProblem("bit))", 3);
			ba.assertNonProblem("foux)", 4);
			cpp= !cpp;
		} while(cpp);
	}
	
	//    void f1(int& r) {}
	//    void f2(const int& r) {}
	//    void f3(volatile int& r) {}
	//    void f4(const volatile int& r) {}
	//
	//    void ref() {
	//    	int i= 1;
	//    	const int ci= 1;
	//    	volatile int vi= 2;
	//    	const volatile int cvi = 3;
	//    	
	//    	f1(i);
	//    	f1(ci); // should be an error
	//    	f1(vi); // should be an error
	//    	f1(cvi); // should be an error
	//    	
	//    	f2(i);
	//    	f2(ci);
	//    	f2(vi); // should be an error
	//    	f2(cvi); // should be an error
	//    	
	//    	f3(i);
	//    	f3(ci); // should be an error
	//    	f3(vi);
	//    	f3(cvi); // should be an error
	//    	
	//    	f4(i); 
	//    	f4(ci);
	//    	f4(vi);
	//    	f4(cvi);
	//    }
	public void testBug222418_a() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ba.assertNonProblem("f1(i)",2);
		ba.assertProblem("f1(ci)",  2);
		ba.assertProblem("f1(vi)",  2);
		ba.assertProblem("f1(cvi)", 2);
		
		ba.assertNonProblem("f2(i)", 2);
		ba.assertNonProblem("f2(ci)",2);
		ba.assertProblem("f2(vi)",   2);
		ba.assertProblem("f2(cvi)",  2);
		
		ba.assertNonProblem("f3(i)", 2);
		ba.assertProblem("f3(ci)",   2);
		ba.assertNonProblem("f3(vi)",2);
		ba.assertProblem("f3(cvi)",  2);
		
		ba.assertNonProblem("f4(i)",  2);
		ba.assertNonProblem("f4(ci)", 2);
		ba.assertNonProblem("f4(vi)", 2);
		ba.assertNonProblem("f4(cvi)",2);
	}
	
	//    void f1(int& r) {} // 1
	//    void f1(const int& r) {} // 2 
	//    void f1(volatile int& r) {} // 3
	//    void f1(const volatile int& r) {} // 4
	//
	//    void ref() {
	//    	int i= 1;
	//    	const int ci= 1;
	//    	volatile int vi= 2;
	//    	const volatile int cvi = 3;
	//    	
	//    	f1(i); // (1)
	//    	f1(ci); // (2) 
	//    	f1(vi); // (3)
	//    	f1(cvi); // (4)
	//    }
	public void testBug222418_b() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		
		ICPPFunction f1_1= ba.assertNonProblem("f1(i)",  2, ICPPFunction.class);
		ICPPFunction f1_2= ba.assertNonProblem("f1(ci)", 2, ICPPFunction.class);
		ICPPFunction f1_3= ba.assertNonProblem("f1(vi)", 2, ICPPFunction.class);
		ICPPFunction f1_4= ba.assertNonProblem("f1(cvi)",2, ICPPFunction.class);
		
		assertEquals(ASTTypeUtil.getParameterTypeString(f1_1.getType()), "(int &)");
		assertEquals(ASTTypeUtil.getParameterTypeString(f1_2.getType()), "(const int &)");
		assertEquals(ASTTypeUtil.getParameterTypeString(f1_3.getType()), "(volatile int &)");
		assertEquals(ASTTypeUtil.getParameterTypeString(f1_4.getType()), "(const volatile int &)");
	}
	
	//    void f1(int r) {} // 1
	//    void f1(const int r) {} // 2 
	//    void f1(volatile int r) {} // 3
	//    void f1(const volatile int r) {} // 4
	public void testBug222418_b_regression() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);    	
		ba.assertNonProblem("f1(int",  2, ICPPFunction.class);
		ba.assertProblem("f1(const i", 2);
		ba.assertProblem("f1(vol", 2);
		ba.assertProblem("f1(const v",2);
	}
	
	//    void fa(int& r) {}
	//    void fb(const int& r) {}
	//    void fc(volatile int& r) {}
	//    void fd(const volatile int& r) {}
	//
	//    int five() { return 5; }
	//
	//    void ref() {
	//    	fa(5); // should be an error
	//    	fb(5); // ok 
	//    	fc(5); // should be an error
	//    	fd(5); // should be an error
	//
	//    	fb(five()); // ok
	//      fa(five()); // should be an error 
	//    	fc(five()); // should be an error
	//    	fd(five()); // should be an error
	//    }
	public void testBug222418_c() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);    	
		
		ICPPFunction fn= ba.assertNonProblem("five() {", 4, ICPPFunction.class);
		assertFalse(fn.getType().getReturnType() instanceof IProblemBinding);
		
		ba.assertProblem("fa(5",  2);
		ICPPFunction fb= ba.assertNonProblem("fb(5", 2, ICPPFunction.class);
		ba.assertProblem("fc(5", 2);
		ba.assertProblem("fd(5",2);
		
		ICPPFunction fb2= ba.assertNonProblem("fb(f", 2, ICPPFunction.class);
		ba.assertProblem("fa(f",2);
		ba.assertProblem("fc(f",2);
		ba.assertProblem("fd(f",2);
		
		assertEquals(ASTTypeUtil.getParameterTypeString(fb.getType()), "(const int &)");
		assertEquals(ASTTypeUtil.getParameterTypeString(fb2.getType()), "(const int &)");
	}
	
	//	class X {
	//		public:
	//			X(int x) {}
	//	};
	//
	//	void f_const(const X& x) {}
	//	void f_nonconst(X& x) {}
	//
	//	void ref() {
	//		f_const(2);    // ok
	//		f_nonconst(2); // should be an error
	//	}
	public void testBug222418_d() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);    	
		ba.assertNonProblem("f_const(2",  7, ICPPFunction.class);
		ba.assertProblem("f_nonconst(2",  10);
	}
	
	//	class A {};
	//
	//	void f1(A& r) {}
	//	void f2(const A& r) {}
	//	void f3(volatile A& r) {}
	//	void f4(const volatile A& r) {}
	//
	//	void ref() {
	//		A i= *new A();
	//		const A ci= *new A();
	//		volatile A vi= *new A();
	//		const volatile A cvi = *new A();
	//
	//		f1(i);
	//		f1(ci);  // should be an error
	//		f1(vi);  // should be an error
	//		f1(cvi); // should be an error
	//
	//		f2(i);
	//		f2(ci);
	//		f2(vi);  // should be an error
	//		f2(cvi); // should be an error
	//
	//		f3(i);
	//		f3(ci);  // should be an error
	//		f3(vi);
	//		f3(cvi); // should be an error
	//
	//		f4(i); 
	//		f4(ci);
	//		f4(vi);
	//		f4(cvi);
	//	}
	public void testBug222418_e() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ba.assertNonProblem("f1(i)",2);
		ba.assertProblem("f1(ci)",  2);
		ba.assertProblem("f1(vi)",  2);
		ba.assertProblem("f1(cvi)", 2);
		
		ba.assertNonProblem("f2(i)", 2);
		ba.assertNonProblem("f2(ci)",2);
		ba.assertProblem("f2(vi)",   2);
		ba.assertProblem("f2(cvi)",  2);
		
		ba.assertNonProblem("f3(i)", 2);
		ba.assertProblem("f3(ci)",   2);
		ba.assertNonProblem("f3(vi)",2);
		ba.assertProblem("f3(cvi)",  2);
		
		ba.assertNonProblem("f4(i)",  2);
		ba.assertNonProblem("f4(ci)", 2);
		ba.assertNonProblem("f4(vi)", 2);
		ba.assertNonProblem("f4(cvi)",2);
	}
	
	//	class B {};
	//
	//	class A {
	//	public:
	//		A() {}
	//		A(const A&) {}
	//		A(int i) {}
	//		A(B b, int i=5) {}
	//	};
	//
	//	class C {
	//		public:
	//			C() {}
	//		operator A&() {return *new A();}
	//	};
	//
	//	class D {
	//		public:
	//			D() {}
	//		operator A() {return *new A();}
	//	};
	//
	//
	//	void foo1(A a) {}
	//	void foo2(A& a) {}
	//
	//	int refs() {
	//		A a;
	//		B b;
	//		C c;
	//		D d;
	//
	//		foo1(a); 
	//		foo2(a); // not copied
	//
	//		foo1(3); 
	//		foo2(4); // should be an error (222418)
	//
	//		foo1(A(5));
	//		foo2(A(6)); // should be an error (222418)
	//
	//		foo1(c);
	//		foo2(c);
	//
	//		foo1(d);
	//		foo2(d); // should be an error
	//
	//		foo1(b); 
	//		foo2(b); // should be an error
	//
	//		return 0;
	//	}
	public void testBug222418_f() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true); 
		ba.assertNonProblem("foo1(a)", 4);
		ba.assertNonProblem("foo2(a)", 4);
		ba.assertNonProblem("foo1(3)", 4);
		ba.assertProblem("foo2(4)", 4);
		ba.assertNonProblem("foo1(A(", 4);
		ba.assertProblem("foo2(A(", 4);
		ba.assertNonProblem("foo1(c)", 4);
		ba.assertNonProblem("foo2(c)", 4);
		ba.assertNonProblem("foo1(d)", 4);
		ba.assertProblem("foo2(d)", 4);
		ba.assertNonProblem("foo1(b)", 4);
		ba.assertProblem("foo2(b)", 4);
	}
	
	
	// class A {};
	// class B : public A {};
	//
	// void f(const A) {}
	// void ref() {
	//    B b;
	//    const B& b2= b;
	//    f(b2);
	// }
	public void testBug222418_g_regression() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ba.assertNonProblem("f(b2)", 1);
	}
	
	// void f(int &i) {}
	// void ref() {
	//    int a=3;
	//    int& r= a;
	//    f(r);
	// }
	public void testBug222418_h_regression() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ba.assertNonProblem("f(r)", 1);
	}
	
	// class A {};
	// class B : public A {};
	// void f(A &a) {}
	// void ref() {
	//    B b;
	//    B& b2= b;
	//    f(b2);
	// }
	public void testBug222418_i_regression() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ba.assertNonProblem("f(b2)", 1);
	}
	
	//  // test adapted from IndexNamesTests.testReadWriteFlagsCpp()
	// 
	//	int ri, wi, rwi, ridebug;
	//	int* rp; int* wp; int* rwp; 
	//	int* const rpc= 0;
	//	const int * const rcpc= 0;
	//	void fi(int);
	//	void fp(int*);
	//	void fr(int&);
	//	void fcp(const int*);
	//	void fcr(const int&);
	//	void fpp(int**);
	//	void fpr(int*&);
	//	void fcpp(int const**);
	//	void fcpr(int const*&);
	//	void fpcp(int *const*);
	//	void fpcr(int *const&);
	//	void fcpcp(int const *const*);
	//	void fcpcr(int const *const&);
	//
	//	int test() {
	//		fi(ri); fp(&rwi); fcp(&ri); 
	//		fi(*rp); fp(rp); fcp(rp); fpp(&rwp); fpcp(&rpc); fcpcp(&rcpc); 
	//		fr(rwi); fcr(ri);
	//		fpcr(&rwi); fcpcr(&ri);
	//		fpr(rwp); fpcr(rp); fcpcr(rp);
	//		
	//		fcpp(&rwp);     // should be error 
	//		fpr(&rwi);      // should be error
	//		fcpr(&ridebug); // should be error
	//		fcpr(rwp);      // should be error
	//		
	//		return ri;
	//	}
	public void testBug222418_j() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		
		ba.assertNonProblem("fi(ri)", 2);
		ba.assertNonProblem("fp(&rwi)", 2);
		ba.assertNonProblem("fcp(&ri)", 3);
		
		ba.assertNonProblem("fi(*rp)", 2);
		ba.assertNonProblem("fp(rp)", 2);
		ba.assertNonProblem("fcp(rp)", 3);
		ba.assertNonProblem("fpp(&rwp)", 3);
		ba.assertNonProblem("fpcp(&rpc)", 4);
		ba.assertNonProblem("fcpcp(&rcpc)", 5);
		
		ba.assertNonProblem("fr(rwi)", 2);
		ba.assertNonProblem("fcr(ri)", 3);
		
		ba.assertNonProblem("fpcr(&rwi)", 4);
		ba.assertNonProblem("fcpcr(&ri)", 5);
		
		ba.assertNonProblem("fpr(rwp)", 3);
		ba.assertNonProblem("fpcr(rp)", 4);
		ba.assertNonProblem("fcpcr(rp)", 5);
		
		ba.assertProblem("fcpp(&rwp)", 4);
		ba.assertProblem("fpr(&rwi)", 3);
		ba.assertProblem("fcpr(&ridebug)", 4);
		ba.assertProblem("fcpr(rwp)", 4);
	}
	
	// class A {
	//    public:
	//       void foo() {}
	//       void foo() const {}
	// };
	// void ref() {
	//    A a;
	//    a.foo();
	// }
	public void testBug222418_k_regression() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ba.assertNonProblem("foo();", 3);
	}
	
	//	class B {};
	//
	//	class C { 
	//	public: 
	//		operator const B() const { return *new B();}
	//	}; 
	//
	//	void foo(B b) {}
	//
	//	void refs() {
	//		const C c= *new C();
	//		const B b= *new B();
	//
	//		foo(b); 
	//		foo(c); 
	//	}
	public void _testBug222444_a() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ICPPFunction foo1= ba.assertNonProblem("foo(b", 3, ICPPFunction.class);
		ICPPFunction foo2= ba.assertNonProblem("foo(c", 3, ICPPFunction.class);
	}
	
	//	class A {};
	//  class B : public A {};
	// 
	//	class C { 
	//	public: 
	//		operator const B() { return *new B();}
	//	}; 
	//
	//	void foo(A a) {}
	//
	//	void refs() {
	//		C c= *new C();
	// 
	//		foo(c);
	//	}
	public void _testBug222444_b() throws Exception {
		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);
		ICPPFunction foo2= ba.assertNonProblem("foo(c", 3, ICPPFunction.class);
	}
	
	// int a, b, c;
	// void test() {
	//    a= b ? : c;
	// }
	public void testOmittedPositiveExpression_Bug212905() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C);
		parseAndCheckBindings(code, ParserLanguage.CPP);
	}
	
	// #define __inline__ __inline__ __attribute__((always_inline))
	// typedef int __u32;
	// static __inline__  __u32 f(int x) {
	//   return x;
	// }
	public void testRedefinedGCCKeywords_Bug226112() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	//	int foo asm ("myfoo") __attribute__((__used__)), ff asm("ss") = 2;
	//	extern void func () asm ("FUNC") __attribute__((__used__)); 
	public void testASMLabels_Bug226121() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	// void test() {
	//    ({1;}) != 0;
	// }
	public void testCompoundStatementExpression_Bug226274() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}		
	
	// void test(int count) {
	//    __typeof__(count) a= 1;
	//    int ret0 = ((__typeof__(count)) 1);
	// }
	public void testTypeofUnaryExpression_Bug226492() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	// void test(int count) {
	//    typeof(count==1) a= 1;
	// }
	public void testTypeofExpression_Bug226492() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	//	void func() {
	//	  typeof(__attribute__((regparm(3)))void (*)(int *)) a;
	//	} 
	public void testTypeofExpressionWithAttribute_Bug226492() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	// void test(int count) {
	//    switch(count) {
	//       case 1 ... 3: break;
	//    }
	// }
	public void testCaseRange_Bug211882() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	//	template<typename T> class X {
	//		typename T::t func() {
	//			return typename T::t();
	//		}
	//	};
	public void testTypenameInExpression() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.CPP);
	}
	
	//	struct __attribute__((declspec)) bla;
	public void testAttributeInElaboratedTypeSpecifier_Bug227085() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C, true);
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	// struct X;
	// void test(struct X* __restrict result);
	public void testRestrictReference_Bug227110() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.CPP, true);
	}
	
	// char buf[256];
	// int x= sizeof(buf)[0];
	public void testSizeofUnaryWithParenthesis_Bug227122() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.C);
		parseAndCheckBindings(code, ParserLanguage.CPP);
	}	
	
	//	class X {
	//		public:
	//			void* operator new(unsigned int sz, char* buf) {return buf;}
	//	};
	//
	//	char* buffer;
	//	void test1() {
	//		X* it = buffer == 0 ?  new (buffer) X : 0;
	//	}
	public void testPlacementNewInConditionalExpression_Bug227104() throws Exception {
		final String code = getAboveComment();
		parseAndCheckBindings(code, ParserLanguage.CPP);
	}	
	
    // int f(x) {
    //    return 0;
    // }
    public void testBug228422_noKnrParam() throws Exception {
    	StringBuffer buffer = getContents(1)[0];
    	parse(buffer.toString(), ParserLanguage.C, false );
    }
    
    //    struct {
    //    	char foo;
    //    } myStruct, *myStructPointer;
    //
    //    union {
    //    	char foo;
    //    } myUnion, *myUnionPointer;
    //
    //    void test() {
    //    	myStruct.foo=1;
    //    	myStructPointer->foo=2;
    //    	myUnion.foo=3;
    //    	myUnionPointer->foo=4;
    //
    //    	myStruct.bar=1;
    //    	myStructPointer->bar=2;
    //    	myUnion.bar=3;
    //    	myUnionPointer->bar=4;
    //    }  
    public void testBug228504_nonExistingMembers() throws Exception {
    	boolean[] isCpps= {true, false};
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);
    		for (int i=1; i < 5; i++) {
				ba.assertNonProblem("foo=" + i, 3);
				ba.assertProblem("bar=" + i, 3);
			}
		}
    }
    
    //    struct S {
    //    	int value; 
    //    };
    //    typedef struct S *PtrS; 
    //    struct T {
    //    	PtrS ptrS;  
    //    };
    //
    //    void testint(int x);
    //    void testptr(struct T* x);
    //
    //    void test() {
    //    	struct T* t;
    //    	t->ptrS->value;
    //    	(t->ptrS+1)->value;
    //    	testptr(t-0);
    //    	testint(t-t);
    //    	testint(0-t);
    //    }  
    public void testTypeOfPointerOperations() throws Exception {
    	String code= getAboveComment();
    	parseAndCheckBindings(code, ParserLanguage.C);
    	parseAndCheckBindings(code, ParserLanguage.CPP);
    }
    
    //    int globalArray[4] = {1,2,3,4};
    //    void function1(); // decl
    //
    //    void function1() {
    //        getArray()[0] = 1; 
    //    }
    //
    //    void function2() {
    //        function1(); // ref
    //    }
    public void testOutOfOrderResolution_Bug232300() throws Exception {
    	final boolean[] isCpp= {false, true};
    	String code= getAboveComment();
    	for (boolean element : isCpp) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), element);
    		IBinding b1= ba.assertNonProblem("function1(); // decl", 9);
    		IBinding b2= ba.assertNonProblem("function1() {", 9);
    		IBinding b3= ba.assertNonProblem("function1(); // ref", 9);
    		assertSame(b1, b2);
    		assertSame(b2, b3);
		}
    }
    
    // #define foo __typeof__((int*)0 - (int*)0)
    // typedef foo ptrdiff_t;
    public void testRedefinePtrdiff_Bug230895() throws Exception {
    	final boolean[] isCpps= {false, true};
    	String code= getAboveComment();
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);
    		IBinding b1= ba.assertNonProblem("ptrdiff_t", 9);
    		assertInstance(b1, ITypedef.class);
    		ITypedef td= (ITypedef) b1;
    		IType t= td.getType();
    		assertFalse(t instanceof ITypedef);
		}    	
    }
    
    // int a;
    // int b= a; // ref
    // struct S;
    // typedef struct S S; // td
    public void testRedefineStructInScopeThatIsFullyResolved() throws Exception {
    	final boolean[] isCpps= {false, true};
    	String code= getAboveComment();
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);
    		ba.assertNonProblem("a; // ref", 1);
    		// now scope is fully resolved
    		ICompositeType ct= ba.assertNonProblem("S;", 1, ICompositeType.class);
    		ITypedef td= ba.assertNonProblem("S; // td", 1, ITypedef.class);
    		IType t= td.getType();
    		assertFalse(t instanceof IProblemBinding);
    		assertSame(t, ct);
		}    	
    }
    
    //    void checkLong(long);
    //    void test() {
    //    	checkLong(__builtin_expect(1, 1));
    //    }
    public void testReturnTypeOfBuiltin_Bug234309() throws Exception {
    	String code= getAboveComment();
    	parseAndCheckBindings(code, ParserLanguage.C, true);
    	parseAndCheckBindings(code, ParserLanguage.CPP, true);
    }
    
    //    typedef void VOID;
    //    VOID func(VOID) {
    //    }
    public void testTypedefVoid_Bug221567() throws Exception {
    	final boolean[] isCpps= {false, true};
    	String code= getAboveComment();
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);
    		ITypedef td= ba.assertNonProblem("VOID;", 4, ITypedef.class);
    		IBinding ref= ba.assertNonProblem("VOID)", 4);
    		assertSame(td, ref);
    		
    		IFunction func= ba.assertNonProblem("func", 4, IFunction.class);
    		IFunctionType ft= func.getType();
    		IType rt= ft.getReturnType();
    		IType[] pts= ft.getParameterTypes();
    		assertEquals(1, pts.length);
			IType pt = pts[0];
			assertInstance(rt, ITypedef.class);
			assertInstance(pt, ITypedef.class);
			rt= ((ITypedef)rt).getType();
			pt= ((ITypedef)pt).getType();

			assertTrue(rt instanceof IBasicType);
    		assertEquals(IBasicType.t_void, ((IBasicType)rt).getType());
    		assertTrue(pt instanceof IBasicType);
    		assertEquals(IBasicType.t_void, ((IBasicType)pt).getType());
    	}
    }
    
    //    #define str(s) # s
    //
    //    void foo() {
    //    	printf(str(this is a // this should go away
    //    	string));	
    //    }
    public void testCommentInExpansion_Bug84276() throws Exception {
    	IASTTranslationUnit tu= parseAndCheckBindings(getAboveComment());
    	IASTFunctionDefinition func= (IASTFunctionDefinition) tu.getDeclarations()[0];
    	
    	IASTFunctionCallExpression fcall= (IASTFunctionCallExpression) ((IASTExpressionStatement)((IASTCompoundStatement) func.getBody()).getStatements()[0]).getExpression();
    	IASTLiteralExpression lit= (IASTLiteralExpression) fcall.getParameterExpression();
    	assertEquals("\"this is a string\"", lit.toString());
    }
    
    // typedef int tint;
    // tint f1(tint (tint));
    // int f2(int (int));
    // int f3(int (tint));
    // int f4(int (identifier));
    // int f5(int *(tint[10])); 
    public void testParamWithFunctionType_Bug84242() throws Exception {
    	final String comment= getAboveComment();
    	final boolean[] isCpps= {false, true};
    	for (boolean isCpp : isCpps) {
        	BindingAssertionHelper ba= new BindingAssertionHelper(comment, isCpp);

        	IFunction f= ba.assertNonProblem("f1", 2, IFunction.class);
        	isTypeEqual(f.getType(), "int (int (int) *)");

        	f= ba.assertNonProblem("f2", 2, IFunction.class);
        	isTypeEqual(f.getType(), "int (int (int) *)");

        	f= ba.assertNonProblem("f3", 2, IFunction.class);
        	isTypeEqual(f.getType(), "int (int (int) *)");

        	f= ba.assertNonProblem("f4", 2, IFunction.class);
        	isTypeEqual(f.getType(), "int (int)");
        	
        	f= ba.assertNonProblem("f5", 2, IFunction.class);
        	isTypeEqual(f.getType(), "int (int * (int *) *)");
    	}
    }

    // class C { };
    // void f1(int(C)) { }     
    public void testParamWithFunctionTypeCpp_Bug84242() throws Exception {
    	BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), true);

    	IFunction f= ba.assertNonProblem("f1", 2, IFunction.class);
    	isTypeEqual(f.getType(), "void (int (C) *)");
    }

    // int (*f1(int par))[5] {};
    // int (*f1 (int par))[5];
    public void testFunctionReturningPtrToArray_Bug216609() throws Exception {
    	final String comment= getAboveComment();
    	final boolean[] isCpps= {false, true};
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);

    		IFunction f= ba.assertNonProblem("f1", 2, IFunction.class);
    		isTypeEqual(f.getType(), "int [] * (int)");
    		
    		f= ba.assertNonProblem("f1 ", 2, IFunction.class);
    		isTypeEqual(f.getType(), "int [] * (int)");
    	}
    }
    
    // void f1(){}
    // void (f2)(){}
    // void (f3()){}
    // void ((f4)()){}
    // void f1();
    // void (f2)();
    // void (f3());
    // void ((f4)());
    public void testNestedFunctionDeclarators() throws Exception {
    	final String comment= getAboveComment();
    	final boolean[] isCpps= {false, true};
    	for (ParserLanguage lang: ParserLanguage.values()) {
    		IASTTranslationUnit tu= parseAndCheckBindings(comment, lang);
    		IASTFunctionDefinition fdef= getDeclaration(tu, 0);
    		IASTDeclarator dtor= fdef.getDeclarator();
    		assertNull(dtor.getNestedDeclarator());
    		assertInstance(dtor.getParent(), IASTFunctionDefinition.class);
    		assertInstance(dtor.getName().resolveBinding(), IFunction.class);

    		fdef= getDeclaration(tu, 1);
    		dtor= fdef.getDeclarator();
    		assertNotNull(dtor.getNestedDeclarator());
    		assertInstance(dtor.getParent(), IASTFunctionDefinition.class);
    		assertInstance(dtor.getNestedDeclarator().getName().resolveBinding(), IFunction.class);

    		fdef= getDeclaration(tu, 2);
    		dtor= fdef.getDeclarator();
    		assertNull(dtor.getNestedDeclarator());
    		assertInstance(dtor.getParent().getParent(), IASTFunctionDefinition.class);
    		assertInstance(dtor.getName().resolveBinding(), IFunction.class);

    		fdef= getDeclaration(tu, 3);
    		dtor= fdef.getDeclarator();
    		assertNotNull(dtor.getNestedDeclarator());
    		assertInstance(dtor.getParent().getParent(), IASTFunctionDefinition.class);
    		assertInstance(dtor.getNestedDeclarator().getName().resolveBinding(), IFunction.class);

    		IASTSimpleDeclaration sdef= getDeclaration(tu, 4);
    		IBinding binding= sdef.getDeclarators()[0].getName().resolveBinding();
    		assertInstance(binding, IFunction.class);
    		assertEquals(2, tu.getDeclarationsInAST(binding).length);
    		
    		sdef= getDeclaration(tu, 5);
    		binding= sdef.getDeclarators()[0].getNestedDeclarator().getName().resolveBinding();
    		assertInstance(binding, IFunction.class);
    		assertEquals(2, tu.getDeclarationsInAST(binding).length);

    		sdef= getDeclaration(tu, 6);
    		binding= sdef.getDeclarators()[0].getNestedDeclarator().getName().resolveBinding();
    		assertInstance(binding, IFunction.class);
    		assertEquals(2, tu.getDeclarationsInAST(binding).length);

    		sdef= getDeclaration(tu, 7);
    		binding= sdef.getDeclarators()[0].getNestedDeclarator().getNestedDeclarator().getName().resolveBinding();
    		assertInstance(binding, IFunction.class);
    		assertEquals(2, tu.getDeclarationsInAST(binding).length);
    	}
    }
    
    //  void f() {                    
    //    int a,b;                     
    //    { b; a; int a; }              
    //  }
    public void _testLocalVariableResolution_Bug235831() throws Exception {
    	final String comment= getAboveComment();
    	final boolean[] isCpps= {false, true};
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);

    		ba.assertNonProblem("b; a", 1, IVariable.class);	// fill cache of inner block
    		IVariable v3= ba.assertNonProblem("a; }", 1, IVariable.class);
    		IVariable v2= ba.assertNonProblem("a; int", 1, IVariable.class);
    		IVariable v1= ba.assertNonProblem("a,", 1, IVariable.class);
    		assertSame(v1, v2);
    		assertNotSame(v2, v3);
    	}
    }
    
    // int foo(int (*ptr) (int, int));
    public void testComplexParameterBinding_Bug214482() throws Exception {
    	final String comment= getAboveComment();
    	final boolean[] isCpps= {false, true};
    	for (boolean isCpp : isCpps) {
    		BindingAssertionHelper ba= new BindingAssertionHelper(getAboveComment(), isCpp);
    		IParameter p= ba.assertNonProblem("ptr", 3, IParameter.class);
    		assertEquals("ptr", p.getName());
    	}
    }
    
    // void test() {}
    // +error
    public void testTrailingSyntaxErrorInTU() throws Exception {
    	final String comment= getAboveComment();
    	for (ParserLanguage lang : ParserLanguage.values()) {
    		IASTTranslationUnit tu= parse(comment, lang, false, false);
    		IASTDeclaration decl= getDeclaration(tu, 0);
    		IASTProblemDeclaration pdecl= getDeclaration(tu, 1);
    		assertEquals("+error", pdecl.getRawSignature());
    	}
    }

    // struct X {
    // int test;
    // +error
    // };
    public void testTrailingSyntaxErrorInCompositeType() throws Exception {
    	final String comment= getAboveComment();
    	for (ParserLanguage lang : ParserLanguage.values()) {
    		IASTTranslationUnit tu= parse(comment, lang, false, false);
    		IASTCompositeTypeSpecifier ct= getCompositeType(tu, 0);
    		IASTDeclaration decl= getDeclaration(ct, 0);
    		IASTProblemDeclaration pdecl= getDeclaration(ct, 1);
    		assertEquals("+error", pdecl.getRawSignature());
    	}
    }
    
    // void func() {
    //    {
    //       int test;
    //       +error
    //    }
    // }
    public void testTrailingSyntaxErrorInCompoundStatements() throws Exception {
    	final String comment= getAboveComment();
    	for (ParserLanguage lang : ParserLanguage.values()) {
    		IASTTranslationUnit tu= parse(comment, lang, false, false);
    		IASTFunctionDefinition def= getDeclaration(tu, 0);
    		IASTCompoundStatement compStmt= getStatement(def, 0);
    		IASTDeclarationStatement dstmt= getStatement(compStmt, 0);
    		IASTProblemStatement pstmt= getStatement(compStmt, 1);
    		assertEquals("+error", pstmt.getRawSignature());
    	}
    }
    
    // struct X {
    //  ;
    // };
    // ;
    public void testEmptyDeclarations() throws Exception {
    	final String comment= getAboveComment();
    	for (ParserLanguage lang : ParserLanguage.values()) {
    		IASTTranslationUnit tu= parse(comment, lang, false, false);
    		IASTCompositeTypeSpecifier ct= getCompositeType(tu, 0);
    		IASTDeclaration empty= getDeclaration(ct, 0);
    		assertEquals(";", empty.getRawSignature());
    		empty= getDeclaration(tu, 1);
    		assertEquals(";", empty.getRawSignature());
    	}
    }
}
