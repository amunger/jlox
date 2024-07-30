import sys

if len(sys.argv) == 2:
    outputDir = sys.argv[1]
else:
    outputDir = "../com/craftinginterpreters/lox"

def defineAst(baseName, types):
    path = outputDir + "/" + baseName + ".java"
    with open(path, "w") as file:
        file.write("package com.craftinginterpreters.lox;\n\n")
        file.write("import java.util.List;\n\n")
        file.write("abstract class " + baseName + " {\n\n");

        defineVisitor(file, baseName, types)

        for typeName in types:
            className = typeName.split(":")[0].strip()
            fields = typeName.split(":")[1].strip()
            defineType(file, baseName, className, fields)

        file.write("  abstract <R> R accept(Visitor<R> visitor);\n")

        file.write("}\n")

def defineVisitor(file, baseName, types):
    file.write("  interface Visitor<R> {\n")

    for typeName in types:
        typeName = typeName.split(":")[0].strip()
        file.write("    R visit" + typeName + baseName + "(" + typeName + " " + baseName.lower() + ");\n")

    file.write("}\n\n")

def defineType(file, baseName, className, fieldList):
    file.write("  static class " + className + " extends " + baseName + " {\n")

    fields = fieldList.split(", ")
    for field in fields:
        file.write("    final " + field + ";\n")

    file.write("\n")
    file.write("    " + className + "(" + fieldList + ") {\n")

    for field in fields:
        fieldName = field.split(" ")[1]
        file.write("      this." + fieldName + " = " + fieldName + ";\n")

    file.write("    }\n\n")

    file.write("    @Override <R> R accept(Visitor<R> visitor) {\n")
    file.write("      return visitor.visit" + className + baseName + "(this);\n")
    file.write("    }\n")

    file.write("  }\n\n")

defineAst("Expr", [
    "Binary   : Expr left, Token operator, Expr right",
    "Grouping : Expr expression",
    "Literal  : Object value",
    "Unary    : Token operator, Expr right",
])