package ie.wit.myapplication.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * abstract base class for database task operation outside of main thread
 */

abstract class BaseFragment : Fragment(),CoroutineScope{
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job +Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }



}