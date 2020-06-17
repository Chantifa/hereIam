package ch.ffhs.esa.hereiam.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ch.ffhs.esa.hereiam.model.Entry
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

interface DatabaseService {
    fun getAllEntries(): LiveData<List<Entry>>
    fun addEntry(heading: String, text: String)
}

class DatabaseServiceFirestore : DatabaseService {
    private val collection = "entriesV3"
    private val fbFirestore = FirebaseFirestore.getInstance().collection(collection)

    private val sortBy = "entryLastModified"
    private val sortDir = Query.Direction.DESCENDING
    private val limit = 20L

    override fun addEntry(heading: String, text: String) {
        val entry = Entry(heading, text, "LocationName TODO", 0.0, 0.0)
        Timber.i("added: $entry")
        fbFirestore.add(entry)
            .addOnCompleteListener { task ->
                // TODO: User feedback
                if (task.isSuccessful) {
                    Timber.i("success")
                } else {
                    Timber.i("error ${task.exception?.message!!}")
                }
            }
    }

    override fun getAllEntries(): LiveData<List<Entry>> {
        val list = ArrayList<Entry>()
        val task = fbFirestore.orderBy(sortBy, sortDir).limit(limit).get()
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    Timber.i("${doc.toObject(Entry::class.java)}")
                    list.add(doc.toObject(Entry::class.java))
                }
            } else {
                // TODO return exception
                Timber.i(it.exception?.message!!)
            }
        }
        val data = MutableLiveData<List<Entry>>()
        data.value = list
        return data
    }
}