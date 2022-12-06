package com.example.chat2.ui.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chat2.adapters.ChatAdapter
import com.example.chat2.databinding.FragmentChatBinding
import com.example.chat2.model.ChatUser
import com.example.chat2.showToast
import com.google.firebase.firestore.FirebaseFirestore


class ChatFragment : Fragment() {
    lateinit var chatUser: ChatUser
    private lateinit var auth: FirebaseFirestore
    lateinit var chatList: ArrayList<ChatUser>
    private lateinit var adapter: ChatAdapter
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseFirestore.getInstance()
        chatList = arrayListOf()
        adapter = ChatAdapter(chatList)
        binding.recyclerChat.adapter = adapter
        chatListChange()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun chatListChange() {
        auth.collection("Users").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    chatUser = data.toObject(ChatUser::class.java)!!
                    if (chatUser != null) {
                        chatList.add(chatUser)
                    }
                }
                adapter.notifyDataSetChanged()

            }
        }.addOnFailureListener {
            showToast(it.toString())
        }


    }

}
