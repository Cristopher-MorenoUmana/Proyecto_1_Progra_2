package com.mycompany.proyecto_1_progra_ii;

public enum Orientation {
    
     HORIZONTAL('h'), VERTICAL('v');
     
     char orientationValue;
     
     Orientation(char pOrientationValue){
         this.orientationValue = pOrientationValue;
     }
     
     public char getOrientationValue(){
         return this.orientationValue;
     }
}
