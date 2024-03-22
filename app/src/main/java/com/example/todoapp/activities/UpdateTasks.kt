package com.example.todoapp.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.todoapp.R
import com.example.todoapp.adapters.PageAdapter
import com.example.todoapp.databinding.ActivityUpdateTasksBinding

class UpdateTasks : AppCompatActivity(), View.OnClickListener {
    private lateinit var def: ColorStateList
    private lateinit var item1: TextView
    private lateinit var item2: TextView
    private lateinit var select: TextView

    private lateinit var binding: ActivityUpdateTasksBinding
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: androidx.appcompat.widget.Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        item1 = findViewById(R.id.completed)
        item2 = findViewById(R.id.notCompleted)
        item1.setOnClickListener(this)
        item2.setOnClickListener(this)
        select = findViewById(R.id.select)
        def = item2.textColors

        viewPager = findViewById<ViewPager>(R.id.viewPager)

        Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()

//        binding.completedBtn.setOnClickListener {
//            if (viewPager.currentItem != 0) {
//                viewPager.setCurrentItem(viewPager.currentItem - 1, true);
//                Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()
//            }
//        }
//        binding.notCompleteBtn.setOnClickListener {
//            if (viewPager.currentItem != 1) {
//
//                viewPager.setCurrentItem(viewPager.currentItem + 1, true);
//                Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()
//            }
//        }
        viewPager.adapter = PageAdapter(supportFragmentManager)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.completed -> {
                select.animate().x(0f).duration = 100
                item1.setTextColor(Color.WHITE)
                item2.setTextColor(def)
                if (viewPager.currentItem != 0) {
                    viewPager.setCurrentItem(viewPager.currentItem - 1, true);
                    Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.notCompleted -> {
                item1.setTextColor(def)
                item2.setTextColor(Color.WHITE)
                val size = item2.width
                select.animate().x(size.toFloat()).duration = 100
                if (viewPager.currentItem != 1) {

                    viewPager.setCurrentItem(viewPager.currentItem + 1, true);
                    Toast.makeText(this, "${viewPager.currentItem}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}