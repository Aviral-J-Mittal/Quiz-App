package com.example.btechquiz.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.btechquiz.R
import com.example.btechquiz.adapter.QuestionsAdapter
import com.example.btechquiz.databinding.FragmentQuestionsBinding
import com.example.btechquiz.navigator.ItemQuestionsClicked
import com.example.btechquiz.utility.HelperQuestions
import com.example.btechquiz.utility.RecyclerHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class QuestionsFragment : Fragment(), ItemQuestionsClicked {
    private var mFragmentQuestionsBinding: FragmentQuestionsBinding? = null
    private var pagerSnapHelper: PagerSnapHelper? = null
    private var adapter: QuestionsAdapter? = null
    private var mRecyclerHelper: RecyclerHelper? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentQuestionsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questions,
            container,
            false
        )
        val bundle = arguments
        val timeMessage = bundle!!.getInt("testTime")
        val quesList = HelperQuestions.queList
        val linear = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mFragmentQuestionsBinding!!.questionRecycler.layoutManager = linear
        adapter = QuestionsAdapter(quesList!!, requireContext(), this)
        mFragmentQuestionsBinding!!.questionRecycler.adapter = adapter
        RecyclerHelper.recyclerItem = mFragmentQuestionsBinding!!.questionRecycler
        pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper!!.attachToRecyclerView(mFragmentQuestionsBinding!!.questionRecycler)
        val bookMark = requireActivity().findViewById<ImageView>(R.id.bookMark)
        requireActivity().findViewById<TextView>(R.id.quesNo).text =
            "1".plus("/").plus(quesList.size.toString())
        val clearButton = requireActivity().findViewById<Button>(R.id.clear)
        mRecyclerHelper = RecyclerHelper(
            pagerSnapHelper!!, requireActivity().findViewById(R.id.quesNo), clearButton, bookMark
        )
        mFragmentQuestionsBinding!!.questionRecycler.addOnScrollListener(mRecyclerHelper!!)
        val finish = requireActivity().findViewById<Button>(R.id.finish)
        finish.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(false)
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            val cancel = view.findViewById<Button>(R.id.cancel)
            val yes = view.findViewById<Button>(R.id.yes)
            builder.setView(view)
            val alertDialog = builder.create()
            alertDialog.show()
            cancel.setOnClickListener {
                alertDialog.dismiss()
            }
            yes.setOnClickListener {
                val remainingTime = bundle.getLong("remainingTime")
                startActivity(
                    Intent(
                        requireActivity(),
                        ScoreActivity::class.java
                    ).putExtra("TIME_TAKEN", (timeMessage * 60 * 1000) - remainingTime)
                )
                requireActivity().finish()
            }
        }

        bookMark.setOnClickListener {



                setBookMarks(HelperQuestions.queList!![RecyclerHelper.quesId!!].queID)
                Toast.makeText(requireActivity(),"Bookmarked!",Toast.LENGTH_SHORT).show()

        }
        return mFragmentQuestionsBinding!!.root
    }

    override fun onClicked(
        imgABtn: ImageButton,
        imgBBtn: ImageButton,
        imgCBtn: ImageButton,
        imgDBtn: ImageButton,
        optPos: String,
        pos: Int
    ) {
        HelperQuestions.queList!![pos].status = 4
        HelperQuestions.queList!![pos].selectedAns = optPos
        imgABtn.setBackgroundResource(R.drawable.selected_btn)
        imgBBtn.setBackgroundResource(R.drawable.unselected_btn)
        imgCBtn.setBackgroundResource(R.drawable.unselected_btn)
        imgDBtn.setBackgroundResource(R.drawable.unselected_btn)
    }

    private fun setBookMarks(queDocId: String) {
        val bookDoc=FirebaseFirestore.getInstance().collection("USERS")
            .document(FirebaseAuth.getInstance().uid!!).collection("USER_SCORE")
            .document("BOOKMARKS")
        bookDoc.get().addOnSuccessListener {
            val bookMap= hashMapOf<String,Any>()
            bookMap[queDocId]=queDocId
            if (it.exists())
            {

                val batch=FirebaseFirestore.getInstance().batch()

                batch.update(bookDoc,bookMap)
                batch.commit()
            }
            else{
                FirebaseFirestore.getInstance().collection("USERS")
                    .document(FirebaseAuth.getInstance().uid!!).collection("USER_SCORE")
                    .document("BOOKMARKS").set(bookMap)
            }
        }


    }



}