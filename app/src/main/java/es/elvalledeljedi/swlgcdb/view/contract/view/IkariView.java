package es.elvalledeljedi.swlgcdb.view.contract.view;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public interface IkariView {
    void closeView();
    void enableMenu(boolean enable);
    String getString(int idResource);
    String[] getStringArray(int idResource);
    void showProgressDialog(int idMessage);
    void hideProgressDialog();
}
