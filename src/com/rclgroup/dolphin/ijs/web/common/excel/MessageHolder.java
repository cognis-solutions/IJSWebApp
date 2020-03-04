 /*-----------------------------------------------------------------------------------------------------------
 MessageHolder.java
 -------------------------------------------------------------------------------------------------------------
 Copyright RCL Public Co., Ltd. 2007
 -------------------------------------------------------------------------------------------------------------
 Author NIIT 24/08/17
 - Change Log ------------------------------------------------------------------------------------------------
 ## DD/MM/YY -User-     -TaskRef-      -Short Description
 01 24/08/17  NIIT       IJS            MessageHolder for Excel upload
 -----------------------------------------------------------------------------------------------------------*/

  package com.rclgroup.dolphin.ijs.web.common.excel;
/**
 * This class serves as a repository for the User Messages thrown from the Dao
 * or ejb.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author NIIT
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class  MessageHolder implements Iterator, Serializable
{
	/* To store the MessageHolder Objects.
	*/
	private List mlstMsgHolder;
	private Message mobjCurrentMsg;
	private int mintIteratorCtr;
	 
	public final static int FATAL_INT = 50000;
	public final static int ERROR_INT = 40000;
	public final static int WARN_INT  = 30000;
	public final static int INFO_INT  = 20000;
	private Iterator miterLstIterator;
	
	/**
	 *	Default Constructor
	 *
	 *@return
	 *@exception
	 */

	public MessageHolder()
	{
		mlstMsgHolder = new ArrayList(2);
	}


	/**
	 *	Constructs the New MessageHolder object and add it to the vector.
	 *@param String : Msg Id
	 *@param String[] : Place Holder Parameters
	 *@param int : Severity of the Message
	 *@return
	 *@exception
	 */
	public void add(String astrMsg, String[] aarrPlaceHolders, int aintSeverity)
	{
		mlstMsgHolder.add(new Message(astrMsg, aarrPlaceHolders, aintSeverity));
	}


	/**
	 *Constructs the New MessageHolder object and add it to the vector.
	 *@param String : Msg Id
	 *@param int : Severity of the Message
	 *@return
	 *@exception
	 */
	public void add(String astrMsg, int aintSeverity)
	{
		mlstMsgHolder.add(new Message(astrMsg, null, aintSeverity));
	}


	public Iterator iterator(){
		//reset the message array from holder
		miterLstIterator = mlstMsgHolder.iterator();
		return (Iterator) this;
	}
	
	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return (miterLstIterator.hasNext());
	}
	
	/**
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		mobjCurrentMsg = (Message) miterLstIterator.next();		
		return mobjCurrentMsg;
	}
	
	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		mlstMsgHolder.iterator().remove();
	}

	/**
	 * Returns the Id of the current Message in the collection. Use the iterator
	 * next() method to iterate through the message collection.
	 * 
	 * @return String
	 * @args 
	 * @exception
	 */

	public String getId()
	{
		return mobjCurrentMsg.getId();
	}

	/**
	 * Returns the placeholders of the current Message in the collection. Use
	 * the iterator next() method to iterate through the message collection.
	 * 
	 * @return String[]
	 */
	public String[] getPlaceHolders()
	{
		return mobjCurrentMsg.getPlaceHolders();
	}

	/**
	 * Returns the serverity of the current Message in the collection. Use the
	 * iterator next() method to iterate through the message collection.
	 * 
	 * @return int
	 */
	public int getSeverity()
	{
		return mobjCurrentMsg.getSeverity();
	}
	
	/**
	 * Method size returns the number of Messages in MessageHolder
	 * @return int
	 */
	public int size(){
		return mlstMsgHolder.size();
	}

	//Inner Class 
	private class Message implements Serializable {
		
		/* Stores the Id
		*/
		private String mstrId;
		
		/* Stores the Severity Level of Message
		*/
		private int mintSeverity;
		/* The Place Holder parameters of the Message
		*/
		private String[] marrPlaceHolders;

		/**
		 *	Parameterized Constructor
		 *@param String : Message Id
		 *@param String[] : Place Holder Parameters
		 *@param int : Severity of the Message
		 *@return
		 *@exception
		 */

		private Message(String astrMsg, String[] aarrPlaceHolders, int aintSeverity)
		{
			mstrId = astrMsg;
			marrPlaceHolders = aarrPlaceHolders;
			mintSeverity = aintSeverity;
		}

		/**
		 * Returns the Message Id
		 * @return String
		 * @args 
		 * @exception
		 */

		private String getId()
		{
			return mstrId;
		}

		/**
		 * Returns the Place Holder Parameters 
		 * @return String[]
		 * @args 
		 * @exception
		 */
		private String[] getPlaceHolders()
		{
			return marrPlaceHolders;
		}

		/**
		 * Returns the Severity of the Message
		 * @return int
		 * @args 
		 * @exception
		 */
		private int getSeverity()
		{
			return mintSeverity;
		}

	}
	
}


/* Modification History
 *
 * 2005-07: Modified for Tops
 * 2009-11: Moified for EZL
 *
 */