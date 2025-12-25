package com.example.demo.model;



public class FileMetrics {

    private String fileName;
    private int totalLines;
    private int blankLines;
    private int commentLines;
    private int importCount;
    private int variableCount;
    private int inheritanceCount;
    private int encapsulationCount;
    private int overrideMethods;
    private int overloadedMethods;
    private int cyclomaticComplexity;
    private double complexityPercentage;
    private int tryCount, catchCount, finallyCount;
    private int vowels, consonants, specialChars, totalCharacters;

    public FileMetrics(String fileName, int totalLines, int blankLines, int commentLines, int importCount,
                       int variableCount, int inheritanceCount, int encapsulationCount,
                       int overrideMethods, int overloadedMethods, int cyclomaticComplexity,
                       double complexityPercentage, int tryCount, int catchCount, int finallyCount,
                       int vowels, int consonants, int specialChars, int totalCharacters) {

        this.fileName = fileName;
        this.totalLines = totalLines;
        this.blankLines = blankLines;
        this.commentLines = commentLines;
        this.importCount = importCount;
        this.variableCount = variableCount;
        this.inheritanceCount = inheritanceCount;
        this.encapsulationCount = encapsulationCount;
        this.overrideMethods = overrideMethods;
        this.overloadedMethods = overloadedMethods;
        this.cyclomaticComplexity = cyclomaticComplexity;
        this.complexityPercentage = complexityPercentage;
        this.tryCount = tryCount;
        this.catchCount = catchCount;
        this.finallyCount = finallyCount;
        this.vowels = vowels;
        this.consonants = consonants;
        this.specialChars = specialChars;
        this.totalCharacters = totalCharacters;
    }

    public String getFileName() { return fileName; }
    public int getTotalLines() { return totalLines; }
    public int getBlankLines() { return blankLines; }
    public int getCommentLines() { return commentLines; }
    public int getImportCount() { return importCount; }
    public int getVariableCount() { return variableCount; }
    public int getInheritanceCount() { return inheritanceCount; }
    public int getEncapsulationCount() { return encapsulationCount; }
    public int getOverrideMethods() { return overrideMethods; }
    public int getOverloadedMethods() { return overloadedMethods; }
    public int getCyclomaticComplexity() { return cyclomaticComplexity; }
    public double getComplexityPercentage() { return complexityPercentage; }
    public int getTryCount() { return tryCount; }
    public int getCatchCount() { return catchCount; }
    public int getFinallyCount() { return finallyCount; }
    public int getVowels() { return vowels; }
    public int getConsonants() { return consonants; }
    public int getSpecialChars() { return specialChars; }
    public int getTotalCharacters() { return totalCharacters; }

}




