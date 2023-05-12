package com.example.phonedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {

    private List<Phone> mPhones;

    private LayoutInflater mLayoutInflater;
    private List<Phone> mPhoneList;
    private OnItemClickListener listener;

    public PhoneListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mPhoneList = null;
    }

    public void setPhones(List<Phone> phones) {
        mPhones = phones;
        notifyDataSetChanged();
    }
    public Phone getPosition(int position){
        return mPhones.get(position);
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new PhoneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        if (mPhones != null) {
            Phone currentPhone = mPhones.get(position);
            holder.bind(currentPhone);
        }
    }

    @Override
    public int getItemCount() {
        if (mPhones != null) {
            return mPhones.size();
        }
        return 0;
    }

    class PhoneViewHolder extends RecyclerView.ViewHolder {

        private TextView mManufacturerTextView;
        private TextView mModelTextView;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            mManufacturerTextView = itemView.findViewById(R.id.manufacturer_textview);
            mModelTextView = itemView.findViewById(R.id.model_textview);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(mPhones.get(position));
                }
            });

        }

        public void bind(Phone phone) {
            mManufacturerTextView.setText(phone.getManufacturer());
            mModelTextView.setText(phone.getModel());
            itemView.setTag(phone);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Phone phone);
    }
    public void setOnItemClickListner(OnItemClickListener listener){
        this.listener = listener;
    }
}