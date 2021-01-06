package com.harshit.socialmediaapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.harshit.socialmediaapp.adapter.PostAdapter
import com.harshit.socialmediaapp.R
import com.harshit.socialmediaapp.daos.PostDao
import com.harshit.socialmediaapp.models.Post
import com.harshit.socialmediaapp.models.User
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class Home : AppCompatActivity(), PostAdapter.IPostAdapter {

    lateinit var adapter: PostAdapter
    lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            val alterDialog = androidx.appcompat.app.AlertDialog.Builder(this as Context)
            alterDialog.setTitle("Log out")
            alterDialog.setMessage("are you sure??")
            alterDialog.setPositiveButton("yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()

                val signOutIntent = Intent(this, SignInActivity::class.java)
                startActivity(signOutIntent)
                finish()
            }
            alterDialog.setNegativeButton("no") { _, _ ->

            }
            alterDialog.setCancelable(true)
            alterDialog.create()
            alterDialog.show()
        }



        btnFloat.setOnClickListener {
            val createPostIntent = Intent(this, CreatePostActivity::class.java)
            startActivity(createPostIntent)
        }

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()


        adapter = PostAdapter(recyclerViewOptions, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }

}