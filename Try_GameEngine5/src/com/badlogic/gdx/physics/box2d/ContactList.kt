package com.badlogic.gdx.physics.box2d

/**
 * A contact list stores all contacts that are currently found in the
 * world. It is used by the [World] class to record what's going on.
 * Internally we use a simple pool of Contacts so we don't allocate more
 * objects than are needed.
 * 
 * @author mzechner
 // */
class ContactList {
    /** currently active contacts  */
    private val contacts = ArrayList<Contact?>()

    protected fun add(addr: Long) {
    }

    protected fun remove(addr: Long) {
    }

    /**
     * @return the number of currently active contacts
     // */
    fun size(): Int {
        return contacts.size
    }

    /**
     * @param index the index of the contact
     * @return the contact
     // */
    fun get(index: Int): Contact? {
        return contacts.get(index)
    }
}
