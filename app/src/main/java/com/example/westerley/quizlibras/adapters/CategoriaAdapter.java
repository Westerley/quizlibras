package com.example.westerley.quizlibras.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.westerley.quizlibras.classes.Categoria;
import com.example.westerley.quizlibras.R;
import com.example.westerley.quizlibras.extras.ImageHelper;
import com.example.westerley.quizlibras.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {

    private Context mContext;
    private List<Categoria> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scole;
    private int width;
    private int height;

    public CategoriaAdapter (Context c, List<Categoria> lista) {
        this.mContext = c;
        this.mList = lista;
        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        scole = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scole + 0.5f);
        height = (width / 16) * 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_categoria_card, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_nome.setText(mList.get(position).getNome());
        holder.tv_info.setText(mList.get(position).getInfo());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.img_categoria.setImageResource(mList.get(position).getImagem());
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mList.get(position).getImagem());
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            bitmap = ImageHelper.getRoundedCornerBitmap(mContext, bitmap, 10, width, height, false, false, true, true);
            holder.img_categoria.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         public ImageView img_categoria;
         public TextView tv_nome;
         public TextView tv_info;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_categoria = (ImageView) itemView.findViewById(R.id.img_categoria);
            tv_nome = (TextView) itemView.findViewById(R.id.tv_nome);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }

}
