package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.MenuItem;
import itesm.mx.saludintegral.adapters.MenuItemAdapter;

/**
 * Created by FernandoDavid on 03/05/2018.
 */

public class FragmentoMenuCognicion extends ListFragment implements AdapterView.OnItemClickListener {
    View view;
    private MenuItemAdapter menuItemAdapter;
    private ArrayList<MenuItem> menuItems;
    OnSelectedListener mCallback;

    public FragmentoMenuCognicion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_menu_cognicion, container, false);
        menuItems = getItems();
        menuItemAdapter = new MenuItemAdapter(getActivity(), menuItems);
        setListAdapter(menuItemAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        ListView ls = (ListView) view.findViewById(android.R.id.list);
        ls.setTextFilterEnabled(true);
        registerForContextMenu(ls);
        ls.setOnItemClickListener(this);
        super.onActivityCreated(savedInstance);
    }

    ArrayList<MenuItem> getItems(){
        ArrayList<MenuItem> Aux=new ArrayList<>();
        MenuItem menuItem=new MenuItem("Actividades", R.drawable.medicina_icon);
        Aux.add(menuItem);
        menuItem=new MenuItem("Finanzas", R.drawable.ic_launcher_background);
        Aux.add(menuItem);
        return Aux;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCallback.onSelected(position);
    }

    public interface OnSelectedListener {
        public void onSelected(int i);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (OnSelectedListener) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnSelectedListener.");
            }
        }
    }



}
