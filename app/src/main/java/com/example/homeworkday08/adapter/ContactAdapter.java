package com.example.homeworkday08.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkday08.R;
import com.example.homeworkday08.databinding.ItemContactBinding;
import com.example.homeworkday08.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    List<Contact> contactList;
    IOnClickItem iOnClickItem;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public void setIOnClickItem(IOnClickItem iOnClickItem) {
        this.iOnClickItem = iOnClickItem;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemContactBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_contact, parent, false);

        ContactViewHolder viewHolder = new ContactViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact c = contactList.get(position);
        holder.binding.tvNameContact.setText(c.getName());
        if (c.isImage())
            holder.binding.ivImgContact.setImageURI(Uri.parse(c.getImgUri()));
        else
            holder.binding.ivImgContact.setImageResource(R.drawable.ic_baseline_account_circle_24);

        holder.binding.clItemContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItem.onClickItem(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ItemContactBinding binding;

        public ContactViewHolder(@NonNull ItemContactBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
