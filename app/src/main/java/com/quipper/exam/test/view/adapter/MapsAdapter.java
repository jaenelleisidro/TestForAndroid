package com.quipper.exam.test.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.quipper.exam.test.R;
import com.quipper.exam.test.domain.Map;
import com.quipper.exam.test.model.businesslayer.MapManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MapsAdapter extends EndlessAdapter {
  private RotateAnimation rotate=null;
  private View pendingView=null;
  private MapManager mapManager;
  private Context context;


  public MapsAdapter(Context context, MapManager mapManager) {
    this(context,new ArrayList<Map>(),mapManager);
  }

  public MapsAdapter(Context context, ArrayList<Map> list, MapManager mapManager) {
    super(new ArrayAdapter<Map>(context,
                                    R.layout.adapter_simplelist_row,
                                    R.id.title,
                                    list));
    this.mapManager=mapManager;
    rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                                0.5f, Animation.RELATIVE_TO_SELF,
                                0.5f);
    rotate.setDuration(1000);
    rotate.setRepeatMode(Animation.RESTART);
    rotate.setRepeatCount(Animation.INFINITE);
    this.context=context;
  }
  
  @Override
  protected View getPendingView(ViewGroup parent) {
    View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_simplelist_row, null);
    
    pendingView=row.findViewById(R.id.title);
    pendingView.setVisibility(View.GONE);
    pendingView=row.findViewById(R.id.icon);
    pendingView.setVisibility(View.VISIBLE);
    //startProgressAnimation();
      row.setVisibility(View.GONE);
    return(row);
  }    @Override
       public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        View rowView=null;
        if(convertView==null){
            rowView=super.getView(position,convertView,parent);
            viewHolder=new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.title);
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            viewHolder.description = (TextView) rowView.findViewById(R.id.description);
            rowView.setTag(viewHolder);
        }else{
            rowView=convertView;
            viewHolder=(ViewHolder)rowView.getTag();
        }

        if(position<getWrappedAdapter().getCount()) {
            Map map = (Map) getWrappedAdapter().getItem(position);
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(map.imageUrl).into(viewHolder.imageView);
            viewHolder.title.setText(map.description);
            viewHolder.description.setText("");
        }
        return rowView;
    }




  volatile ArrayList<Map> maps;

    /**
     * returns true if there's still data that can be fetched
     * @return
     */
  @Override
  protected boolean cacheInBackground() {
    //to test for slow netwrok try this -> SystemClock.sleep(10000);
    try {
        maps = mapManager.getMaps(getWrappedAdapter().getCount(), 10);
        return true;
    }catch(RuntimeException e2){
    }catch(Exception e){
    }
      return true;
  }
  
  @Override
  protected void appendCachedData() {
    if (maps!=null && getWrappedAdapter().getCount()< maps.size()) {
      ArrayAdapter<Map> a=(ArrayAdapter<Map>)getWrappedAdapter();
      //if you need to find the index.. a.getCount()-1
      for(Map map:maps){
          a.add(map);
      }
    }
  }
  
  void startProgressAnimation() {
    if (pendingView!=null) {
      pendingView.startAnimation(rotate);
    }
  }

    private class ViewHolder{
        TextView title;
        TextView description;
        ImageView imageView;
    }

}