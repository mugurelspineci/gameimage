package com.technotalkative.listview5;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener {
	
    ListView lview3;
    ListViewCustomAdapter adapter;
    private ArrayList<Object> itemList;
    private ItemBean bean;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
       
        prepareArrayLits();
        lview3 = (ListView) findViewById(R.id.listView1);
        adapter = new ListViewCustomAdapter(this, itemList);
        lview3.setAdapter(adapter);
 
        lview3.setOnItemClickListener(this);
    }
 
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
    	ItemBean bean = (ItemBean) adapter.getItem(position);
    	Toast.makeText(this, "Title => "+bean.getTitle()+" \n Description => "+bean.getDescription(), Toast.LENGTH_SHORT).show();
    }
    
    /* Method used to prepare the ArrayList,
     * Same way, you can also do looping and adding object into the ArrayList.
     */
    public void prepareArrayLits()
    {
    	itemList = new ArrayList<Object>();
    	 
    	AddObjectToList(R.drawable.ic_add, "Add", "Add desc");
    	AddObjectToList(R.drawable.ic_delete, "Delete", "Delete desc");
    	AddObjectToList(R.drawable.ic_down, "Down", "Down desc");
    	AddObjectToList(R.drawable.ic_info, "Information", "Information desc");
    	AddObjectToList(R.drawable.ic_help, "Help", "Help desc");
    	AddObjectToList(R.drawable.ic_download, "Download", "Download desc");
    	AddObjectToList(R.drawable.ic_mail, "Mail", "Mail desc");
    	AddObjectToList(R.drawable.ic_search, "Search", "Search desc");
    	AddObjectToList(R.drawable.ic_settings, "Settings", "Settings desc");
    }
    
    // Add one item into the Array List
    public void AddObjectToList(int image, String title, String desc)
    {
    	bean = new ItemBean();
    	bean.setDescription(desc);
    	bean.setImage(image);
    	bean.setTitle(title);
    	itemList.add(bean);
    }
}