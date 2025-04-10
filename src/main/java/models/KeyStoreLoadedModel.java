package models;

public class KeyStoreLoadedModel {
    private final KeyStoreModel model;
    private final String message;
    public KeyStoreLoadedModel(KeyStoreModel model, String message){
        this.model = model;
        this.message = message;

    }
    public String getMessage(){
        return this.message;
    }
    public KeyStoreModel getModel(){
        return this.model;
    }
    public boolean isSuccess(){
        return this.model != null;
    }


}
