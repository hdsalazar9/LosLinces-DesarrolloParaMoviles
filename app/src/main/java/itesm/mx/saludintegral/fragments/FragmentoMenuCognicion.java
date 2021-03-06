package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.MenuItem;
import itesm.mx.saludintegral.adapters.MenuItemAdapter;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 03/05/2018.
 */

public class FragmentoMenuCognicion extends ListFragment implements AdapterView.OnItemClickListener {
    View view;
    private MenuItemAdapter menuItemAdapter;
    private ArrayList<MenuItem> menuItems;
    OnSelectedListener mCallback;
    FrameLayout frameLayout;
    Boolean bSoloUna = true;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Miscellaneous.refresh(menuItemAdapter);
        }
    };

    public FragmentoMenuCognicion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_menu_cognicion, container, false);

        frameLayout = view.findViewById(R.id.frameLayout);

        menuItems = getItems();
        menuItemAdapter = new MenuItemAdapter(getActivity(), menuItems);

        final ViewTreeObserver observer= frameLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Log.d("Log", "Height: " + frameLayout.getHeight());
                        if(bSoloUna) {
                            Miscellaneous.iSizeSubMenu = frameLayout.getHeight();;
                            bSoloUna = false;
                            menuItemAdapter.notifyDataSetChanged();
                        }
                    }
                });

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
        MenuItem menuItem=new MenuItem("Actividades", R.drawable.light);
        Aux.add(menuItem);
        menuItem=new MenuItem("Finanzas", R.drawable.money);
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
    public void onResume() {
        super.onResume();
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