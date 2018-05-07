package itesm.mx.saludintegral.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.activities.SaludActivity;
import itesm.mx.saludintegral.adapters.MedicamentoAdapter;
import itesm.mx.saludintegral.adapters.MenuItem;
import itesm.mx.saludintegral.adapters.MenuItemAdapter;
import itesm.mx.saludintegral.util.Miscellaneous;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoMenuSalud extends ListFragment implements AdapterView.OnItemClickListener{
    View view;
    private MenuItemAdapter menuItemArrayAdapter;
    private ArrayList<MenuItem> menuItems;
    OnSelectedListener mCallback;

    FrameLayout frameLayout;
    Boolean bSoloUna;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Miscellaneous.refresh(menuItemArrayAdapter);
        }
    };

    public FragmentoMenuSalud() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_menu_salud, container, false);

        bSoloUna = true;
        frameLayout = view.findViewById(R.id.frameLayout);
        final ViewTreeObserver observer= frameLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Log.d("Log", "Height: " + frameLayout.getHeight());
                        Miscellaneous.iSizeMenu = frameLayout.getHeight();
                        if(bSoloUna) {
                            handler.postDelayed(runnable, 1);
                            bSoloUna = false;
                        }
                    }
                });

        menuItems=getItems();
        menuItemArrayAdapter=new MenuItemAdapter(getActivity(), menuItems);
        setListAdapter(menuItemArrayAdapter);
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
        MenuItem menuItem=new MenuItem("Medicinas", R.drawable.medicina_icon);
        Aux.add(menuItem);
        menuItem=new MenuItem("Monitoreo de sueño", R.drawable.ic_launcher_background);
        Aux.add(menuItem);
        menuItem=new MenuItem("Alimentación", R.drawable.ic_launcher_background);
        Aux.add(menuItem);
        menuItem=new MenuItem("Ejercicio", R.drawable.ic_launcher_background);
        Aux.add(menuItem);
        return Aux;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //MenuItem menuItem = (MenuItem) parent.getItemAtPosition(position);
        Intent intent;
        mCallback.onSelected(position);
        /*switch (position) {
            case 0:
                mCallback.onSelected();
                FragmentoMedicamento fragmentoMedicamento=new FragmentoMedicamento();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_ActivitySalud, fragmentoMedicamento);
                transaction.addToBackStack(null);

                transaction.commit();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }*/
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
                        " must implement OnVolverListener.");
            }
        }
    }

}
