package sv.ub.com.taller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DK-Ragnar on 2/9/2018.
 */

public class CustomAdapter extends ArrayAdapter<DataModelItem> implements View.OnClickListener{

    private ArrayList<DataModelItem> dataSet;
    Context context;

    private static class ViewHolder{
        TextView txtName;
        TextView txtValue;
        TextView txtCategoria;
    }

    public CustomAdapter(ArrayList<DataModelItem> data, Context context){
        super(context, R.layout.conceptos_item, data);

        this.context = context;
        this.dataSet = data;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModelItem dataModel=(DataModelItem) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataModelItem dataModelItem = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.conceptos_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtValue = (TextView) convertView.findViewById(R.id.value);
            viewHolder.txtCategoria = (TextView) convertView.findViewById(R.id.categoria);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModelItem.getName());
        viewHolder.txtValue.setText("$"+dataModelItem.getValue());
        viewHolder.txtCategoria.setText(dataModelItem.getCategoria());

        return convertView;
    }
}
