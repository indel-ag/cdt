/*******************************************************************************
* Copyright (c) 2006, 2010 IBM Corporation and others.
*
* This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*     IBM Corporation - initial API and implementation
*********************************************************************************/

// This file was generated by LPG

package org.eclipse.cdt.internal.core.dom.lrparser.c99;

public interface C99ExpressionParsersym {
	public final static int TK_auto = 24, TK_break = 81, TK_case = 82, TK_char = 31, TK_const = 6, TK_continue = 83,
			TK_default = 84, TK_do = 85, TK_double = 32, TK_else = 86, TK_enum = 44, TK_extern = 25, TK_float = 33,
			TK_for = 87, TK_goto = 88, TK_if = 89, TK_inline = 26, TK_int = 34, TK_long = 35, TK_register = 27,
			TK_restrict = 7, TK_return = 90, TK_short = 36, TK_signed = 37, TK_sizeof = 16, TK_static = 17,
			TK_struct = 45, TK_switch = 91, TK_typedef = 28, TK_union = 46, TK_unsigned = 38, TK_void = 39,
			TK_volatile = 8, TK_while = 92, TK__Bool = 40, TK__Complex = 41, TK__Imaginary = 42, TK_integer = 18,
			TK_floating = 19, TK_charconst = 20, TK_stringlit = 21, TK_identifier = 1, TK_Completion = 3,
			TK_EndOfCompletion = 5, TK_Invalid = 93, TK_LeftBracket = 12, TK_LeftParen = 2, TK_LeftBrace = 13,
			TK_Dot = 52, TK_Arrow = 67, TK_PlusPlus = 14, TK_MinusMinus = 15, TK_And = 11, TK_Star = 4, TK_Plus = 9,
			TK_Minus = 10, TK_Tilde = 22, TK_Bang = 23, TK_Slash = 53, TK_Percent = 54, TK_RightShift = 47,
			TK_LeftShift = 48, TK_LT = 55, TK_GT = 56, TK_LE = 57, TK_GE = 58, TK_EQ = 61, TK_NE = 62, TK_Caret = 63,
			TK_Or = 64, TK_AndAnd = 65, TK_OrOr = 68, TK_Question = 69, TK_Colon = 59, TK_DotDotDot = 49,
			TK_Assign = 60, TK_StarAssign = 70, TK_SlashAssign = 71, TK_PercentAssign = 72, TK_PlusAssign = 73,
			TK_MinusAssign = 74, TK_RightShiftAssign = 75, TK_LeftShiftAssign = 76, TK_AndAssign = 77,
			TK_CaretAssign = 78, TK_OrAssign = 79, TK_Comma = 29, TK_RightBracket = 50, TK_RightParen = 30,
			TK_RightBrace = 43, TK_SemiColon = 66, TK_ERROR_TOKEN = 51, TK_EOF_TOKEN = 80;

	public final static String orderedTerminalSymbols[] = { "", "identifier", "LeftParen", "Completion", "Star",
			"EndOfCompletion", "const", "restrict", "volatile", "Plus", "Minus", "And", "LeftBracket", "LeftBrace",
			"PlusPlus", "MinusMinus", "sizeof", "static", "integer", "floating", "charconst", "stringlit", "Tilde",
			"Bang", "auto", "extern", "inline", "register", "typedef", "Comma", "RightParen", "char", "double", "float",
			"int", "long", "short", "signed", "unsigned", "void", "_Bool", "_Complex", "_Imaginary", "RightBrace",
			"enum", "struct", "union", "RightShift", "LeftShift", "DotDotDot", "RightBracket", "ERROR_TOKEN", "Dot",
			"Slash", "Percent", "LT", "GT", "LE", "GE", "Colon", "Assign", "EQ", "NE", "Caret", "Or", "AndAnd",
			"SemiColon", "Arrow", "OrOr", "Question", "StarAssign", "SlashAssign", "PercentAssign", "PlusAssign",
			"MinusAssign", "RightShiftAssign", "LeftShiftAssign", "AndAssign", "CaretAssign", "OrAssign", "EOF_TOKEN",
			"break", "case", "continue", "default", "do", "else", "for", "goto", "if", "return", "switch", "while",
			"Invalid" };

	public final static boolean isValidForParser = true;
}
