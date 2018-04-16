package itesm.mx.saludintegral.adapters;

/**
 * Clase MenuItem:
 * Contiene la clase de los items de menu que se utilizarán
 * en las distintas pantallas de selección/menu de la aplicación.
 *
 * Contiene un título (nombre de la selección),
 * y un int referente al id de Drawable de la imagen
 * a desplegar en el boton.
 */

public class MenuItem {
    private String sTitle;
    private int iImage;

    public MenuItem(String sTitle, int iImage){
        this.sTitle=sTitle;
        this.iImage=iImage;
    }

    public String getTitle(){
        return this.sTitle;
    }

    public int getImage(){
        return this.iImage;
    }
}
