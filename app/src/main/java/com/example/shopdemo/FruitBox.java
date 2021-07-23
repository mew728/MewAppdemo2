package com.example.shopdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FruitBox extends Fragment {
    private static final String ITEM_TITLE = "Name";
    private static final String ITEM_TITLE2 = "Mark";
    private static final String ITEM_TITLE3 = "Price";
    private static final String ITEM_TITLE4 = "Quantity";
    private static final String ITEM_ICON = "Icon";
    private ListView fruitbox_listview;
    String[] fruitboxlist,fruitboxpricelist,fruitboxmarklist ,fruitboxquantitylist;
    TypedArray  fruitboxicon;
    public FruitBox() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fruitbox, container, false);
        //
        fruitbox_listview = (ListView) view.findViewById(R.id.fruitbox_listview);
        fruitboxlist = getResources().getStringArray(R.array.fruitbox_list);
        fruitboxmarklist = getResources().getStringArray(R.array.mark_type2_list);
        fruitboxpricelist = getResources().getStringArray(R.array.fruitbox_price_list);
        fruitboxquantitylist =getResources().getStringArray(R.array.mark_g_list);
        fruitboxicon = getResources().obtainTypedArray(R.array.fruitbox_icon_list);
        //
        List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < fruitboxlist.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_TITLE,  fruitboxlist[i]);
            item.put(ITEM_TITLE2,  fruitboxmarklist[i]);
            item.put(ITEM_TITLE3,  fruitboxpricelist[i]);
            item.put(ITEM_TITLE4,  fruitboxquantitylist[i]);
            item.put(ITEM_ICON,  fruitboxicon.getResourceId(i, 0));
            itemList.add(item);
        }
        // ArrayAdapter adapter= new ArrayAdapter(getContext(),R.layout.fragment_seasonfruit_listview,itemList);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), itemList, R.layout.fragment_fruitbox_listview,
                new String[]{ITEM_TITLE,ITEM_TITLE2,ITEM_TITLE3,ITEM_TITLE4, ITEM_ICON},
                new int[]{R.id.txtView_box,R.id.txtView_box2, R.id.txtView_box3,R.id.txtView_box4,R.id.imgView_box});
        fruitbox_listview.setAdapter(adapter);
        fruitbox_listview.setOnItemClickListener(listViewOnItemClick);
        return view;
    }

    private AdapterView.OnItemClickListener listViewOnItemClick
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.fragment_seasonfruit_dialogwithseek, getActivity().findViewById(R.id.dialog_season_layout));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("商品:"+fruitboxlist[i])
                    .setMessage("商品價格:$"+fruitboxpricelist[i])
                    .setCancelable(true)
                    .setIcon(android.R.drawable.star_big_on).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setNegativeButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setView(layout);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            SeekBar sb = (SeekBar)layout.findViewById(R.id.dialog_season_seekbar);
            TextView showtotal=layout.findViewById(R.id.dialog_season_textview);
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int k, boolean b){
                    //Do something here with new value
                    String total="總價:";
                    int price=Integer.parseInt(fruitboxpricelist[i])*k;
                    String Sprice=Integer.toString(price);
                    showtotal.setText(total+Sprice+"/"+String.valueOf(k)+"盒");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
    };
}
