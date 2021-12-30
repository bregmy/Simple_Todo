package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOError
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks= mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //Remove the item from the list
                listOfTasks.removeAt(position)
                //Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        //lets detect when the user clicks on the add button
        //findViewById<Button>(R.id.button2).setOnClickListener {

            //This code will let us know if the button is been clicked by user or not.
            //Log.i("Caren","User clicked on button")

        //}

        loaditems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input field so that user can enter the task
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button2).setOnClickListener{
            //1. Grab the text that user has inputted
            val userInputtedTask= inputTextField.text.toString()

            //2. Add the string to our list of task
            listOfTasks.add(userInputtedTask)
            // Notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            //3. reset the text fields
            inputTextField.setText("")

            saveItems()



        }
    }
    //Save the data that the user has inputted
    //Save a data by writing and reading from a file
    //Get a File
    fun getDataFile(): File{

        return File(filesDir, "data.txt")
    }
    //load th item by reading every line in our file
    fun loaditems(){
        try{
            listOfTasks=FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
    //Save items by writing into our data file
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(),listOfTasks)
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}