package Otros;

import javafx.scene.input.KeyEvent;

public class Validaciones {
    private char a;
    
    public void QuitarLetrasYSignos(KeyEvent event) {
        a= event.getCharacter().charAt(0);
        
        if (!(Character.isDigit(a) || (a=='.') )){
            event.consume();
        } 
    }
    
    public void QuitarNumerosYSignos (KeyEvent event){
        a= event.getCharacter().charAt(0);
        
        if (!(Character.isLetter(a) || Character.isSpaceChar(a))){
            event.consume();
        } 
    }
    
    public void QuitarLetrasYTodosLosSignos (KeyEvent event){
        a= event.getCharacter().charAt(0);
        
        if (!(Character.isDigit(a) || Character.isSpaceChar(a)) || a==' '){
            event.consume();
        } 
    }
    
    public void QuitarLetrasYTodosLosSignosCodigo (KeyEvent event){
        a = event.getCharacter().charAt(0);
        
        if (!(Character.isDigit(a) || Character.isSpaceChar(a)) || a==' ' )
            event.consume();
    }
    
    public void QuitarCaracteresEspeciales (KeyEvent event){
        a = event.getCharacter().charAt(0);
        
        if (!(Character.isDigit(a) || Character.isLetter(a) || Character.isSpaceChar(a)))
            event.consume();
    }
    
}
