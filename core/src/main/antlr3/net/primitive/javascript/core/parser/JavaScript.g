/*
  Copyright 2008 Chris Lambrou.
  All rights reserved.
*/
grammar JavaScript;

options {
  output    = AST;
  backtrack = true;
  memoize   = true;
}

tokens {
  IF       = 'if';
  FUNCTION = 'function';
  VAR      = 'var';
}

@header {
package net.primitive.javascript.core.parser;

import net.primitive.javascript.core.ast.*;
import net.primitive.javascript.core.*;
}

@lexer::header {
package net.primitive.javascript.core.parser;
}

program returns [Program result]
  :
  
  {
   $result = new Program();
  }
  LT!* sourceElements 
                     {
                      $result.addAstNode($sourceElements.result);
                     }
  LT!* EOF!
  ;

sourceElements returns [AstNodeList result]
  :
  
  {
   $result = new AstNodeList();
  }
  (LT!* sourceElement 
                     {
                      $result.addAstNode($sourceElement.result);
                     })*
  ;

sourceElement returns [AstNode result]
  :
  functionDeclaration 
                     {
                      $result = $functionDeclaration.result;
                     }
  | statement 
             {
              $result = $statement.result;
             }
  ;

// functions

functionDeclaration returns [AstNode result]
  :
  FUNCTION LT!* Identifier LT!* formalParameterList LT!* functionBody 
                                                                     {
                                                                      $result = new FunctionDeclaration($Identifier.text,
                                                                      		$formalParameterList.result, $functionBody.result);
                                                                     }
  ;

formalParameterList returns [List result]
  :
  
  {
   $result = new ArrayList();
  }
  '(' (LT!* (i1=Identifier 
                          {
                           $result.add($i1.text);
                          }
      (LT!* ',' LT!* i2=Identifier 
                                  {
                                   $result.add($i2.text);
                                  })*))? LT!* ')'
  ;

functionBody returns [AstNodeList result]
  :
  '{' LT!* sourceElements 
                         {
                          $result = $sourceElements.result;
                         }
  LT!* '}'
  ;

// statements

statement returns [AstNode result]
  :
  statementBlock 
                {
                 $result = $statementBlock.result;
                }
  | variableStatement 
                     {
                      $result = $variableStatement.result;
                     }
  | emptyStatement
  | expressionStatement 
                       {
                        $result = $expressionStatement.result;
                       }
  | ifStatement 
               {
                $result = $ifStatement.result;
               }
  | iterationStatement 
                      {
                       $result = $iterationStatement.result;
                      }
  | continueStatement 
                     {
                      $result = $continueStatement.result;
                     }
  | breakStatement 
                  {
                   $result = $breakStatement.result;
                  }
  | returnStatement 
                   {
                    $result = $returnStatement.result;
                   }
  | withStatement 
                 {
                  $result = $withStatement.result;
                 }
  | labelledStatement 
                     {
                      $result = $labelledStatement.result;
                     }
  | switchStatement 
                   {
                    $result = $switchStatement.result;
                   }
  | throwStatement 
                  {
                   $result = $throwStatement.result;
                  }
  | tryStatement 
                {
                 $result = $tryStatement.result;
                }
  ;

statementBlock returns [AstNodeList result]
  :
  '{' LT!* statementList? LT!* '}' 
                                  {
                                   $result = $statementList.result;
                                  }
  ;

statementList returns [AstNodeList result]
  :
  
  {
   $result = new AstNodeList();
  }
  st1=statement 
               {
                $result.addAstNode($st1.result);
               }
  (LT!* st2=statement 
                     {
                      $result.addAstNode($st2.result);
                     })*
  ;

variableStatement returns [AstNode result]
  :
  VAR LT!* variableDeclarationList 
                                  {
                                   $result = $variableDeclarationList.result;
                                  }
  (
    LT
    | ';'
  )!
  ;

variableDeclarationList returns [AstNodeList result]
  :
  
  {
   $result = new AstNodeList();
  }
  v1=variableDeclaration 
                        {
                         $result.addAstNode($v1.result);
                        }
  (LT!* ',' LT!* v2=variableDeclaration 
                                       {
                                        $result.addAstNode($v2.result);
                                       })*
  ;

variableDeclarationListNoIn returns [AstNodeList result]
  :
  
  {
   $result = new AstNodeList();
  }
  v1=variableDeclarationNoIn 
                            {
                             $result.addAstNode($v1.result);
                            }
  (LT!* ',' LT!* v2=variableDeclarationNoIn 
                                           {
                                            $result.addAstNode($v2.result);
                                           })*
  ;

variableDeclaration returns [VariableDeclaration result]
  :
  Identifier LT!* initialiser? 
                              {
                               Expression expr = $initialiser.result;
                               if (expr == null) {
                               	expr = new Literal(Undefined.Value);
                               }
                               $result = new VariableDeclaration($Identifier.text, expr);
                              }
  ;

variableDeclarationNoIn returns [VariableDeclaration result]
  :
  Identifier LT!* initialiserNoIn? 
                                  {
                                   Expression expr = $initialiserNoIn.result;
                                   if (expr == null) {
                                   	expr = new Literal(Undefined.Value);
                                   }
                                   $result = new VariableDeclaration($Identifier.text, expr);
                                  }
  ;

initialiser returns [Expression result]
  :
  '=' LT!* assignmentExpression 
                               {
                                $result = $assignmentExpression.result;
                               }
  ;

initialiserNoIn returns [Expression result]
  :
  '=' LT!* assignmentExpressionNoIn 
                                   {
                                    $result = $assignmentExpressionNoIn.result;
                                   }
  ;

emptyStatement
  :
  ';'
  ;

expressionStatement returns [Statement result]
  :
  expression 
            {
             $result = new ExpressionStatement($expression.result);
            }
  (
    LT
    | ';'
  )!
  ;

ifStatement returns [Statement result]
  :
  IF LT!* '(' LT!* expression LT!* ')' LT!* ifstatement=statement (LT!* 'else' LT!* elsestatement=statement)? 
                                                                                                             {
                                                                                                              $result = new IfStatement($expression.result,
                                                                                                              		(AstNodeList) $ifstatement.result, (AstNodeList) $elsestatement.result);
                                                                                                             }
  ;

iterationStatement returns [AstNode result]
  :
  doWhileStatement 
                  {
                   $result = $doWhileStatement.result;
                  }
  | whileStatement 
                  {
                   $result = $whileStatement.result;
                  }
  | forStatement 
                {
                 $result = $forStatement.result;
                }
  | forInStatement 
                  {
                   $result = $forInStatement.result;
                  }
  ;

doWhileStatement returns [Statement result]
  :
  'do' LT!* statement LT!* 'while' LT!* '(' expression ')'
  (
    LT
    | ';'
  )!
  
  {
   $result = new DoWhileStatement($statement.result, $expression.result);
  }
  ;

whileStatement returns [AstNode result]
  :
  'while' LT!* '(' LT!* expression LT!* ')' LT!* statement 
                                                          {
                                                           $result = new WhileStatement($expression.result,
                                                           		(AstNodeList) $statement.result);
                                                          }
  ;

forStatement returns [Statement result]
  :
  'for' LT!* '(' (LT!* forStatementInitialiserPart)? LT!* ';' (LT!* exp1=expression)? LT!* ';' (LT!* exp2=expression)? LT!* ')' LT!* statement 
                                                                                                                                              {
                                                                                                                                               $result = new ForStatement($forStatementInitialiserPart.result, $exp1.result,
                                                                                                                                               		$exp2.result, $statement.result);
                                                                                                                                              }
  ;

forStatementInitialiserPart returns [AstNode result]
  :
  expressionNoIn 
                {
                 $result = new ExpressionStatement($expressionNoIn.result);
                }
  | 'var' LT!* variableDeclarationListNoIn 
                                          {
                                           $result = $variableDeclarationListNoIn.result;
                                          }
  ;

forInStatement returns [Statement result]
  :
  'for' LT!* '(' LT!* forInStatementInitialiserPart LT!* 'in' LT!* expression LT!* ')' LT!* statement 
                                                                                                     {
                                                                                                      $result = new ForInStatement($forInStatementInitialiserPart.result,
                                                                                                      		$expression.result, $statement.result);
                                                                                                     }
  ;

forInStatementInitialiserPart returns [Object result]
  :
  leftHandSideExpression 
                        {
                         $result = $leftHandSideExpression.result;
                        }
  | 'var' LT!* variableDeclarationNoIn 
                                      {
                                       $result = $variableDeclarationNoIn.result;
                                      }
  ;

continueStatement returns [Statement result]
  :
  'continue' Identifier?
  (
    LT
    | ';'
  )!
  
  {
   $result = new ContinueStatement($Identifier.text);
  }
  ;

breakStatement returns [Statement result]
  :
  'break' Identifier?
  (
    LT
    | ';'
  )!
  
  {
   $result = new BreakStatement($Identifier.text);
  }
  ;

returnStatement returns [Statement result]
  :
  'return' expression?
  (
    LT
    | ';'
  )!
  
  {
   $result = new ReturnStatement($expression.result);
  }
  ;

withStatement returns [Statement result]
  :
  'with' LT!* '(' LT!* expression LT!* ')' LT!* statement 
                                                         {
                                                          $result = new WithStatement($expression.result, $statement.result);
                                                         }
  ;

labelledStatement returns [Statement result]
  :
  Identifier LT!* ':' LT!* statement 
                                    {
                                     $result = new LabelledStatement($Identifier.text, $statement.result);
                                    }
  ;

switchStatement returns [Statement result]
  :
  'switch' LT!* '(' LT!* expression LT!* ')' LT!* caseBlock 
                                                           {
                                                            $result = new SwitchStatement($expression.result, $caseBlock.clauses,
                                                            		$caseBlock.dc);
                                                           }
  ;

caseBlock returns [List<CaseClauseStatement> clauses,AstNodeList dc]
@init {
List<CaseClauseStatement> clauses = new ArrayList<CaseClauseStatement>();
}
  :
  '{' (LT!* cc1=caseClause 
                          {
                           clauses.add($cc1.result);
                          })* (LT!* defaultClause 
                                                 {
                                                  $dc = $defaultClause.result;
                                                 }
    (LT!* cc2=caseClause 
                        {
                         clauses.add($cc2.result);
                        })*)? LT!* '}'
  ;

caseClause returns [CaseClauseStatement result]
  :
  'case' LT!* expression LT!* ':' LT!* statementList? 
                                                     {
                                                      $result = new CaseClauseStatement($expression.result, $statementList.result);
                                                     }
  ;

defaultClause returns [AstNodeList result]
  :
  'default' LT!* ':' LT!* statementList? 
                                        {
                                         $result = $statementList.result;
                                        }
  ;

throwStatement returns [Statement result]
  :
  'throw' expression
  (
    LT
    | ';'
  )!
  
  {
   $result = new ThrowStatement($expression.result);
  }
  ;

tryStatement returns [Statement result]
  :
  'try' LT!* statementBlock LT!*
  (
    fc1=finallyClause 
                     {
                      $result = new TryStatement($statementBlock.result, $fc1.result);
                     }
    | catchClause (LT!* fc2=finallyClause)? 
                                           {
                                            $result = new TryStatement($statementBlock.result, $catchClause.result,
                                            		$fc2.result);
                                           }
  )
  ;

catchClause returns [Statement result]
  :
  'catch' LT!* '(' LT!* Identifier LT!* ')' LT!* statementBlock 
                                                               {
                                                                $result = new CatchClause($Identifier.text, $statementBlock.result);
                                                               }
  ;

finallyClause returns [AstNodeList result]
  :
  'finally' LT!* statementBlock 
                               {
                                $result = $statementBlock.result;
                               }
  ;

// expressions

expression returns [Expression result]
@init {
List expressions = new ArrayList();
}
  :
  exp1=assignmentExpression (LT!* ',' LT!* exp2=assignmentExpression)? 
                                                                      {
                                                                       if ($exp2.result == null) {
                                                                       	$result = $exp1.result;
                                                                       } else
                                                                       	$result = new WrappedExpression($exp1.result, $exp2.result);
                                                                      }
  ;

expressionNoIn returns [Expression result]
@init {
List expressions = new ArrayList();
}
  :
  exp1=assignmentExpressionNoIn (LT!* ',' LT!* exp2=assignmentExpressionNoIn)*
  ;
/*
 Chapter 11.3 of ECMA-262
*/


assignmentExpression returns [Expression result]
  :
  conditionalExpression 
                       {
                        $result = $conditionalExpression.result;
                       }
  | leftHandSideExpression LT!* assignmentOperator LT!* rightHandSideExpression=assignmentExpression 
                                                                                                    {
                                                                                                     $result = new AssignmentExpression($leftHandSideExpression.result,
                                                                                                     		$assignmentOperator.result, $rightHandSideExpression.result);
                                                                                                    }
  ;

assignmentExpressionNoIn returns [Expression result]
  :
  conditionalExpressionNoIn 
                           {
                            $result = $conditionalExpressionNoIn.result;
                           }
  | leftHandSideExpression LT!* assignmentOperator LT!* rightHandSideExpression=assignmentExpressionNoIn 
                                                                                                        {
                                                                                                         $result = new AssignmentExpression($leftHandSideExpression.result,
                                                                                                         		$assignmentOperator.result, $rightHandSideExpression.result);
                                                                                                        }
  ;

leftHandSideExpression returns [Expression result]
  :
  callExpression 
                {
                 $result = $callExpression.result;
                }
  | newExpression 
                 {
                  $result = $newExpression.result;
                 }
  ;

newExpression returns [Expression result]
  :
  memberExpression 
                  {
                   $result = $memberExpression.result;
                  }
  | 'new' LT!* exp=newExpression 
                                {
                                 $result = new NewExpression($exp.result);
                                }
  ;

memberExpression returns [Expression result]
@init {
List<Expression> expresionSuffixes = new ArrayList<Expression>();
}
  :
  (
    primaryExpression 
                     {
                      $result = $primaryExpression.result;
                     }
    | functionExpression 
                        {
                         $result = $functionExpression.result;
                        }
    | 'new' LT!* exp=memberExpression LT!* arguments 
                                                    {
                                                     $result = new NewExpression($exp.result, $arguments.result);
                                                    }
  )
  (LT!* memberExpressionSuffix 
                              {
                               expresionSuffixes.add($memberExpressionSuffix.result);
                              })* 
                                 {
                                  $result = new MemberExpression($result, expresionSuffixes);
                                 }
  ;

functionExpression returns [Expression result]
  :
  FUNCTION LT!* Identifier? LT!* formalParameterList LT!* functionBody 
                                                                      {
                                                                       $result = new FunctionExpression($Identifier.text, $formalParameterList.result,
                                                                       		$functionBody.result);
                                                                      }
  ;

memberExpressionSuffix returns [Expression result]
  :
  indexSuffix 
             {
              $result = $indexSuffix.result;
             }
  | propertyReferenceSuffix 
                           {
                            $result = $propertyReferenceSuffix.result;
                           }
  ;

callExpression returns [Expression result]
  :
  memberExpression LT!* arguments (LT!* callExpressionSuffix)* 
                                                              {
                                                               $result = new CallExpression($memberExpression.result, $arguments.result);
                                                              }
  ;

callExpressionSuffix
  :
  arguments
  | indexSuffix
  | propertyReferenceSuffix
  ;

arguments returns [Expression result]
@init {
List<Expression> argsList = new ArrayList<Expression>();
}
  :
  '(' (LT!* exp1=assignmentExpression 
                                     {
                                      argsList.add($exp1.result);
                                     }
    (LT!* ',' LT!* exp2=assignmentExpression 
                                            {
                                             argsList.add($exp2.result);
                                            })*)? LT!* ')' 
                                                          {
                                                           $result = new Arguments(argsList);
                                                          }
  ;

indexSuffix returns [Expression result]
  :
  '[' LT!* expression LT!* ']' 
                              {
                               $result = $expression.result;
                              }
  ;

propertyReferenceSuffix returns [Expression result]
  :
  '.' LT!* Identifier 
                     {
                      $result = new Identifier($Identifier.text);
                     }
  ;

assignmentOperator returns [BinaryOperator result]
  :
  '=' 
     {
      $result = Operators.Assign;
     }
  | '*='
  | '/='
  | '%='
  | '+=' 
        {
         $result = Operators.Plus;
        }
  | '-=' 
        {
         $result = Operators.Minus;
        }
  | '<<='
  | '>>='
  | '>>>='
  | '&='
  | '^='
  | '|='
  ;

conditionalExpression returns [Expression result]
  :
  logicalORExpression (LT!* '?' LT!* exp1=assignmentExpression LT!* ':' LT!* exp2=assignmentExpression)? 
                                                                                                        {
                                                                                                         if ($exp1.result == null && $exp2.result == null) {
                                                                                                         	$result = $logicalORExpression.result;
                                                                                                         } else {
                                                                                                         	$result = new ConditionalExpression($logicalORExpression.result,
                                                                                                         			$exp1.result, $exp2.result);
                                                                                                         }
                                                                                                        }
  ;

conditionalExpressionNoIn returns [Expression result]
  :
  logicalORExpressionNoIn (LT!* '?' LT!* exp1=assignmentExpressionNoIn LT!* ':' LT!* exp2=assignmentExpressionNoIn)? 
                                                                                                                    {
                                                                                                                     if ($exp1.result == null && $exp2.result == null) {
                                                                                                                     	$result = $logicalORExpressionNoIn.result;
                                                                                                                     } else {
                                                                                                                     	$result = new ConditionalExpression($logicalORExpressionNoIn.result,
                                                                                                                     			$exp1.result, $exp2.result);
                                                                                                                     }
                                                                                                                    }
  ;

logicalORExpression returns [Expression result]
  :
  op1=logicalANDExpression 
                          {
                           $result = $op1.result;
                          }
  (LT!* '||' LT!* op2=logicalANDExpression 
                                          {
                                           $result = new BinaryExpression(Operators.LogicalOR, $result, $op2.result);
                                          })*
  ;

logicalORExpressionNoIn returns [Expression result]
  :
  op1=logicalANDExpressionNoIn 
                              {
                               $result = $op1.result;
                              }
  (LT!* '||' LT!* op2=logicalANDExpressionNoIn 
                                              {
                                               $result = new BinaryExpression(Operators.LogicalOR, $result, $op2.result);
                                              })*
  ;

logicalANDExpression returns [Expression result]
  :
  op1=bitwiseORExpression 
                         {
                          $result = $op1.result;
                         }
  (LT!* '&&' LT!* op2=bitwiseORExpression 
                                         {
                                          $result = new BinaryExpression(Operators.LogicalAND, $result, $op2.result);
                                         })*
  ;

logicalANDExpressionNoIn returns [Expression result]
  :
  op1=bitwiseORExpressionNoIn 
                             {
                              $result = $op1.result;
                             }
  (LT!* '&&' LT!* op2=bitwiseORExpressionNoIn 
                                             {
                                              $result = new BinaryExpression(Operators.LogicalAND, $result, $op2.result);
                                             })*
  ;

bitwiseORExpression returns [Expression result]
  :
  op1=bitwiseXORExpression 
                          {
                           $result = $op1.result;
                          }
  (LT!* '|' LT!* op2=bitwiseXORExpression 
                                         {
                                          $result = new BinaryExpression(Operators.BitwiseOR, $result, $op2.result);
                                         })*
  ;

bitwiseORExpressionNoIn returns [Expression result]
  :
  op1=bitwiseXORExpressionNoIn 
                              {
                               $result = $op1.result;
                              }
  (LT!* '|' LT!* op2=bitwiseXORExpressionNoIn 
                                             {
                                              $result = new BinaryExpression(Operators.BitwiseOR, $result, $op2.result);
                                             })*
  ;

bitwiseXORExpression returns [Expression result]
  :
  op1=bitwiseANDExpression 
                          {
                           $result = $op1.result;
                          }
  (LT!* '^' LT!* op2=bitwiseANDExpression 
                                         {
                                          $result = new BinaryExpression(Operators.BitwiseXOR, $result, $op2.result);
                                         })*
  ;

bitwiseXORExpressionNoIn returns [Expression result]
  :
  op1=bitwiseANDExpressionNoIn 
                              {
                               $result = $op1.result;
                              }
  (LT!* '^' LT!* op2=bitwiseANDExpressionNoIn 
                                             {
                                              $result = new BinaryExpression(Operators.BitwiseXOR, $result, $op2.result);
                                             })*
  ;

bitwiseANDExpression returns [Expression result]
  :
  op1=equalityExpression 
                        {
                         $result = $op1.result;
                        }
  (LT!* '&' LT!* op2=equalityExpression 
                                       {
                                        $result = new BinaryExpression(Operators.BitwiseAND, $result, $op2.result);
                                       })*
  ;

bitwiseANDExpressionNoIn returns [Expression result]
  :
  op1=equalityExpressionNoIn 
                            {
                             $result = $op1.result;
                            }
  (LT!* '&' LT!* op2=equalityExpressionNoIn 
                                           {
                                            $result = new BinaryExpression(Operators.BitwiseAND, $result, $op2.result);
                                           })*
  ;

equalityExpression returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=relationalExpression 
                          {
                           $result = $op1.result;
                          }
  (
    LT!*
    (
      '==' 
          {
           operator = Operators.Equals;
          }
      | '!=' 
            {
             operator = Operators.DoesNotEquals;
            }
      | '===' 
             {
              operator = Operators.StrictEquals;
             }
      | '!==' 
             {
              operator = Operators.StrictDoesNotEquals;
             }
    )
    LT!* op2=relationalExpression 
                                 {
                                  $result = new BinaryExpression(operator, $result, $op2.result);
                                 }
  )*
  ;

equalityExpressionNoIn returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=relationalExpressionNoIn 
                              {
                               $result = $op1.result;
                              }
  (
    LT!*
    (
      '==' 
          {
           operator = Operators.Equals;
          }
      | '!=' 
            {
             operator = Operators.DoesNotEquals;
            }
      | '===' 
             {
              operator = Operators.StrictEquals;
             }
      | '!==' 
             {
              operator = Operators.StrictDoesNotEquals;
             }
    )
    LT!* op2=relationalExpressionNoIn 
                                     {
                                      $result = new BinaryExpression(operator, $result, $op2.result);
                                     }
  )*
  ;

relationalExpression returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=shiftExpression 
                     {
                      $result = $op1.result;
                     }
  (
    LT!*
    (
      '<' 
         {
          operator = Operators.LessThan;
         }
      | '>' 
           {
            operator = Operators.GreaterThan;
           }
      | '<=' 
            {
             operator = Operators.LessThanOrEqual;
            }
      | '>=' 
            {
             operator = Operators.GreaterThanOrEual;
            }
      | 'instanceof' 
                    {
                     operator = Operators.InstanceOf;
                    }
      | 'in' 
            {
             operator = Operators.In;
            }
    )
    LT!* op2=shiftExpression 
                            {
                             $result = new BinaryExpression(operator, $result, $op2.result);
                            }
  )*
  ;

relationalExpressionNoIn returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=shiftExpression 
                     {
                      $result = $op1.result;
                     }
  (
    LT!*
    (
      '<' 
         {
          operator = Operators.LessThan;
         }
      | '>' 
           {
            operator = Operators.GreaterThan;
           }
      | '<=' 
            {
             operator = Operators.LessThanOrEqual;
            }
      | '>=' 
            {
             operator = Operators.GreaterThanOrEual;
            }
      | 'instanceof' 
                    {
                     operator = Operators.InstanceOf;
                    }
    )
    LT!* op2=shiftExpression 
                            {
                             $result = new BinaryExpression(operator, $result, $op2.result);
                            }
  )*
  ;

shiftExpression returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=additiveExpression 
                        {
                         $result = $op1.result;
                        }
  (
    LT!*
    (
      '<<' 
          {
           operator = Operators.LeftShift;
          }
      | '>>' 
            {
             operator = Operators.RightShift;
            }
      | '>>>' 
             {
              operator = Operators.UnsignedRightShift;
             }
    )
    LT!* op2=additiveExpression 
                               {
                                $result = new BinaryExpression(operator, $result, $op2.result);
                               }
  )*
  ;

additiveExpression returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=multiplicativeExpression 
                              {
                               $result = $op1.result;
                              }
  (
    LT!*
    (
      '+' 
         {
          operator = Operators.Plus;
         }
      | '-' 
           {
            operator = Operators.Minus;
           }
    )
    LT!* op2=multiplicativeExpression 
                                     {
                                      $result = new BinaryExpression(operator, $result, $op2.result);
                                     }
  )*
  ;

multiplicativeExpression returns [Expression result]
@init {
BinaryOperator operator = null;
}
  :
  op1=unaryExpression 
                     {
                      $result = $op1.result;
                     }
  (
    LT!*
    (
      '*' 
         {
          operator = Operators.Multiply;
         }
      | '/' 
           {
            operator = Operators.Divide;
           }
      | '%' 
           {
            operator = Operators.Modulo;
           }
    )
    LT!* op2=unaryExpression 
                            {
                             $result = new BinaryExpression(operator, $result, $op2.result);
                            }
  )*
  ;

unaryExpression returns [Expression result]
@init {
UnaryOperator operator = null;
}
  :
  postfixExpression 
                   {
                    $result = $postfixExpression.result;
                   }
  |
  (
    'delete' 
            {
             operator = Operators.Delete;
            }
    | 'void'
    | 'typeof' 
              {
               operator = Operators.TypeOf;
              }
    | '++' 
          {
           operator = Operators.PrefixIncrement;
          }
    | '--' 
          {
           operator = Operators.PrefixDecrement;
          }
    | '+' 
         {
          operator = Operators.PlusSigned;
         }
    | '-' 
         {
          operator = Operators.MinusSigned;
         }
    | '~'
    | '!' 
         {
          operator = Operators.Not;
         }
  )
  exp=unaryExpression 
                     {
                      $result = new UnaryExpression(operator, $exp.result);
                     }
  ;

postfixExpression returns [Expression result]
  :
  leftHandSideExpression 
                        {
                         $result = $leftHandSideExpression.result;
                        }
  (
    '++' 
        {
         $result = new UnaryExpression(Operators.PostfixIncrement, $result);
        }
    | '--' 
          {
           $result = new UnaryExpression(Operators.PostfixDecrement, $result);
          }
  )?
  ;

primaryExpression returns [Expression result]
  :
  'this' 
        {
         $result = new This();
        }
  | Identifier 
              {
               $result = new Identifier($Identifier.text);
              }
  | literal 
           {
            $result = $literal.result;
           }
  | arrayLiteral 
                {
                 $result = $arrayLiteral.result;
                }
  | objectLiteral 
                 {
                  $result = $objectLiteral.result;
                 }
  | '(' LT!* expression LT!* ')' 
                                {
                                 $result = $expression.result;
                                }
  ;

// arrayLiteral definition.

arrayLiteral returns [Expression result]
@init {
List values = new ArrayList();
}
  :
  '[' LT!* (firstExp=assignmentExpression 
                                         {
                                          values.add($firstExp.result);
                                         })? (LT!* ',' (LT!* nextExp=assignmentExpression 
                                                                                         {
                                                                                          values.add($nextExp.result);
                                                                                         })?)* LT!* ']' 
                                                                                                       {
                                                                                                        $result = new ArrayLiteral(values);
                                                                                                       }
  ;

// objectLiteral definition.

objectLiteral returns [Expression result]
@init {
List nameValuePairs = new ArrayList();
}
  :
  '{' LT!* (firstPair=propertyNameAndValue 
                                          {
                                           nameValuePairs.add($firstPair.result);
                                          }
    (LT!* ',' LT!* nextPair=propertyNameAndValue 
                                                {
                                                 nameValuePairs.add($nextPair.result);
                                                })*)? LT!* '}' 
                                                              {
                                                               $result = new ObjectLiteral(nameValuePairs);
                                                              }
  ;

propertyNameAndValue returns [NameValuePair result]
  :
  propertyName LT!* ':' LT!* assignmentExpression 
                                                 {
                                                  $result = new NameValuePair($propertyName.text, $assignmentExpression.result);
                                                 }
  ;

propertyName
  :
  Identifier
  | StringLiteral
  | NumericLiteral
  ;

// primitive literal definition.

literal returns [Expression result]
  :
  'null' 
        {
         $result = new Literal(null);
        }
  | 'true' 
          {
           $result = new Literal(Boolean.TRUE);
          }
  | 'false' 
           {
            $result = new Literal(Boolean.FALSE);
           }
  | StringLiteral 
                 {
                  $result = new Literal(Literal.unwrapString($StringLiteral.text));
                 }
  | NumericLiteral 
                  {
                   $result = new Literal(Double.parseDouble($NumericLiteral.text));
                  }
  | RegexpExpressionLiteral
  ;

// lexer rules.

RegexpExpressionLiteral
  :
  '/' ('a' | 'b' )*  '/'
  ;

StringLiteral
  :
  '"' DoubleStringCharacter* '"'
  | '\'' SingleStringCharacter* '\''
  ;

fragment
DoubleStringCharacter
  :
  ~(
    '"'
    | '\\'
    | LT
   )
  | '\\' EscapeSequence
  ;

fragment
SingleStringCharacter
  :
  ~(
    '\''
    | '\\'
    | LT
   )
  | '\\' EscapeSequence
  ;

fragment
EscapeSequence
  :
  CharacterEscapeSequence
  | '0'
  | HexEscapeSequence
  | UnicodeEscapeSequence
  ;

fragment
CharacterEscapeSequence
  :
  SingleEscapeCharacter
  | NonEscapeCharacter
  ;

fragment
NonEscapeCharacter
  :
  ~(
    EscapeCharacter
    | LT
   )
  ;

fragment
SingleEscapeCharacter
  :
  '\''
  | '"'
  | '\\'
  | 'b'
  | 'f'
  | 'n'
  | 'r'
  | 't'
  | 'v'
  ;

fragment
EscapeCharacter
  :
  SingleEscapeCharacter
  | DecimalDigit
  | 'x'
  | 'u'
  ;

fragment
HexEscapeSequence
  :
  'x' HexDigit HexDigit
  ;

fragment
UnicodeEscapeSequence
  :
  'u' HexDigit HexDigit HexDigit HexDigit
  ;

NumericLiteral
  :
  DecimalLiteral
  | HexIntegerLiteral
  ;

fragment
HexIntegerLiteral
  :
  '0'
  (
    'x'
    | 'X'
  )
  HexDigit+
  ;

fragment
HexDigit
  :
  DecimalDigit
  | ('a'..'f')
  | ('A'..'F')
  ;

fragment
DecimalLiteral
  :
  DecimalDigit+ '.' DecimalDigit* ExponentPart?
  | '.'? DecimalDigit+ ExponentPart?
  ;

fragment
DecimalDigit
  :
  ('0'..'9')
  ;

fragment
ExponentPart
  :
  (
    'e'
    | 'E'
  )
  (
    '+'
    | '-'
  )?
  DecimalDigit+
  ;

Identifier
  :
  IdentifierStart IdentifierPart*
  ;

fragment
IdentifierStart
  :
  UnicodeLetter
  | '$'
  | '_'
  | '\\' UnicodeEscapeSequence
  ;

fragment
IdentifierPart
  :
  (IdentifierStart) => IdentifierStart // Avoids ambiguity, as some IdentifierStart chars also match following alternatives.
  | UnicodeDigit
  | UnicodeConnectorPunctuation
  ;

fragment
UnicodeLetter // Any character in the Unicode categories "Uppercase letter (Lu)",
  :
  'a'..'z'
  | 'A'..'Z'
  ;

fragment
UnicodeCombiningMark // Any character in the Unicode categories "Non-spacing mark (Mn)"
  :
  '\u0300'..'\u034E' // or "Combining spacing mark (Mc)".
  | '\u0360'..'\u0362'
  | '\u0483'..'\u0486'
  | '\u0591'..'\u05A1'
  | '\u05A3'..'\u05B9'
  | '\u05BB'..'\u05BD'
  | '\u05BF'
  | '\u05C1'..'\u05C2'
  | '\u05C4'
  | '\u064B'..'\u0655'
  | '\u0670'
  | '\u06D6'..'\u06DC'
  | '\u06DF'..'\u06E4'
  | '\u06E7'..'\u06E8'
  | '\u06EA'..'\u06ED'
  | '\u0711'
  | '\u0730'..'\u074A'
  | '\u07A6'..'\u07B0'
  | '\u0901'..'\u0903'
  | '\u093C'
  | '\u093E'..'\u094D'
  | '\u0951'..'\u0954'
  | '\u0962'..'\u0963'
  | '\u0981'..'\u0983'
  | '\u09BC'..'\u09C4'
  | '\u09C7'..'\u09C8'
  | '\u09CB'..'\u09CD'
  | '\u09D7'
  | '\u09E2'..'\u09E3'
  | '\u0A02'
  | '\u0A3C'
  | '\u0A3E'..'\u0A42'
  | '\u0A47'..'\u0A48'
  | '\u0A4B'..'\u0A4D'
  | '\u0A70'..'\u0A71'
  | '\u0A81'..'\u0A83'
  | '\u0ABC'
  | '\u0ABE'..'\u0AC5'
  | '\u0AC7'..'\u0AC9'
  | '\u0ACB'..'\u0ACD'
  | '\u0B01'..'\u0B03'
  | '\u0B3C'
  | '\u0B3E'..'\u0B43'
  | '\u0B47'..'\u0B48'
  | '\u0B4B'..'\u0B4D'
  | '\u0B56'..'\u0B57'
  | '\u0B82'..'\u0B83'
  | '\u0BBE'..'\u0BC2'
  | '\u0BC6'..'\u0BC8'
  | '\u0BCA'..'\u0BCD'
  | '\u0BD7'
  | '\u0C01'..'\u0C03'
  | '\u0C3E'..'\u0C44'
  | '\u0C46'..'\u0C48'
  | '\u0C4A'..'\u0C4D'
  | '\u0C55'..'\u0C56'
  | '\u0C82'..'\u0C83'
  | '\u0CBE'..'\u0CC4'
  | '\u0CC6'..'\u0CC8'
  | '\u0CCA'..'\u0CCD'
  | '\u0CD5'..'\u0CD6'
  | '\u0D02'..'\u0D03'
  | '\u0D3E'..'\u0D43'
  | '\u0D46'..'\u0D48'
  | '\u0D4A'..'\u0D4D'
  | '\u0D57'
  | '\u0D82'..'\u0D83'
  | '\u0DCA'
  | '\u0DCF'..'\u0DD4'
  | '\u0DD6'
  | '\u0DD8'..'\u0DDF'
  | '\u0DF2'..'\u0DF3'
  | '\u0E31'
  | '\u0E34'..'\u0E3A'
  | '\u0E47'..'\u0E4E'
  | '\u0EB1'
  | '\u0EB4'..'\u0EB9'
  | '\u0EBB'..'\u0EBC'
  | '\u0EC8'..'\u0ECD'
  | '\u0F18'..'\u0F19'
  | '\u0F35'
  | '\u0F37'
  | '\u0F39'
  | '\u0F3E'..'\u0F3F'
  | '\u0F71'..'\u0F84'
  | '\u0F86'..'\u0F87'
  | '\u0F90'..'\u0F97'
  | '\u0F99'..'\u0FBC'
  | '\u0FC6'
  | '\u102C'..'\u1032'
  | '\u1036'..'\u1039'
  | '\u1056'..'\u1059'
  | '\u17B4'..'\u17D3'
  | '\u18A9'
  | '\u20D0'..'\u20DC'
  | '\u20E1'
  | '\u302A'..'\u302F'
  | '\u3099'..'\u309A'
  | '\uFB1E'
  | '\uFE20'..'\uFE23'
  ;

fragment
UnicodeDigit // Any character in the Unicode category "Decimal number (Nd)".
  :
  '\u0030'..'\u0039'
  | '\u0660'..'\u0669'
  | '\u06F0'..'\u06F9'
  | '\u0966'..'\u096F'
  | '\u09E6'..'\u09EF'
  | '\u0A66'..'\u0A6F'
  | '\u0AE6'..'\u0AEF'
  | '\u0B66'..'\u0B6F'
  | '\u0BE7'..'\u0BEF'
  | '\u0C66'..'\u0C6F'
  | '\u0CE6'..'\u0CEF'
  | '\u0D66'..'\u0D6F'
  | '\u0E50'..'\u0E59'
  | '\u0ED0'..'\u0ED9'
  | '\u0F20'..'\u0F29'
  | '\u1040'..'\u1049'
  | '\u1369'..'\u1371'
  | '\u17E0'..'\u17E9'
  | '\u1810'..'\u1819'
  | '\uFF10'..'\uFF19'
  ;

fragment
UnicodeConnectorPunctuation // Any character in the Unicode category "Connector punctuation (Pc)".
  :
  '\u005F'
  | '\u203F'..'\u2040'
  | '\u30FB'
  | '\uFE33'..'\uFE34'
  | '\uFE4D'..'\uFE4F'
  | '\uFF3F'
  | '\uFF65'
  ;

Comment
  :
  '/*' (options {greedy=false;}: .)* '*/' 
                                         {
                                          $channel = HIDDEN;
                                         }
  ;

LineComment
  :
  '//' ~(LT )* 
              {
               $channel = HIDDEN;
              }
  ;

LT
  :
  (
    '\n' // Line feed.
    | '\r' // Carriage return.
    | '\u2028' // Line separator.
    | '\u2029' // Paragraph separator.
  )
  ;

WhiteSpace // Tab, vertical tab, form feed, space, non-breaking space and any other unicode "space separator".
  :
  (
    '\t'
    | '\v'
    | '\f'
    | ' '
    | '\u00A0'
  )
  
  {
   $channel = HIDDEN;
  }
  ;
