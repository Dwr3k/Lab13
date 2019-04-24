
public class MinHeap <E extends Comparable<? super E>>
{
	private E[] heap;
	private int size = 0;
	private static final int DEFAULT_CAPACITY = 8;

	public MinHeap(int capacity) 
	{
		heap = (E[]) new Comparable[capacity];	 
	}

	public MinHeap()
	{
		this(DEFAULT_CAPACITY);
	}

	public int size() 
	{
		return size;
	}

	public boolean isEmpty() 
	{
		return size() == 0;
	}

	private void expand() 
	{
		E[] newHeap = (E[]) new Comparable[heap.length * 2];

		for (int i = 0; i < size(); i++) 
		{
			newHeap[i] = heap[i];
		}

		heap = newHeap;
	}

	private void swapElements(int p1, int p2) 
	{
		E temp = heap[p1];
		heap[p1] = heap[p2];
		heap[p2] = temp;

	}

	private int getParentIndex(int childIndex) 
	{
		// if odd, child is a left node
		if (childIndex % 2 != 0) 
		{
			return childIndex / 2;
		}
		// if even, child is a right node
		else 
		{
			return childIndex / 2 - 1;
		}
	}

	public void insert(E element) 
	{
		int position = size();

		if(position == heap.length)
		{
			expand();
		}

		size++;
		heap[position] = element;

		int parent = getParentIndex(position);

		while (position > 0 && heap[position].compareTo(heap[parent]) < 0)
		{
			// if parent is greater, swap parent and node
			swapElements(parent, position);
			// update position of the new element and find next parent up
			position = getParentIndex(position);
			parent = getParentIndex(position);
		}

	}

	public E remove() 
	{
		if(isEmpty())
		{
			return null;
		}

		// take out root and place last node at root position
		E min = heap[0];
		heap[0] = heap[size() - 1];
		heap[size() - 1] = null; // optional
		size--;

		// position of new root and its smaller child
		int position = 0;
		int smallerChild = 2;

		// while there is a smaller child, swap parent and child
		//		while (heap[smallerChild] != null) 
		//		{
		//			swapElements(position, smallerChild);
		//			// update position of node and get new smaller child
		//			position = smallerChild;
		//			smallerChild = (2*position) + 2;
		//		}

		E rootValue = heap[0];
		E first = heap[position + 1];
		E second = heap[position + 2];

		while(true)
		{

			if(first != null && second == null)
			{
				if(rootValue.compareTo(first) == 1)
				{
					swapElements(getIndex(rootValue), getIndex(first));
				}

				break;
			}
			else if(second != null && first == null)
			{
				if(rootValue.compareTo(second) == 1)
				{
					swapElements(getIndex(rootValue), getIndex(second));
				}

				break;
			}
			else if(first != null && second != null)
			{
				if(first.compareTo(second) == 1)
				{
					swapElements(getIndex(rootValue), getIndex(second));
				}
				else
				{
					swapElements(getIndex(rootValue), getIndex(first));
				}
			}

			if(isEmpty())
			{
				break;
			}

			position = getIndex(rootValue);
			rootValue = heap[position];

			if((2*position) + 1 > heap.length)
			{
				first = null;
			}
			else
			{
				first = heap[(2 * position) + 1];
			}

			if((2*position) + 2 > heap.length)
			{
				second = null;
			}
			else
			{
				second = heap[(2 * position) + 2];
			}

			if(first == null && second == null)
			{
				break;
			}
		}
		return min;
	}

	public int getIndex(E value)
	{
		for(int i = 0; i < size(); ++i)
		{
			if(heap[i] == value)
			{
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args)
	{
		MinHeap<Integer> mh = new MinHeap<Integer>();

		mh.insert(2);
		mh.insert(4);
		mh.insert(1);
		mh.insert(10);
		mh.insert(3);
		mh.insert(6);
		mh.insert(15);
		mh.insert(12);
		mh.insert(16);
		mh.insert(5);


		while(!mh.isEmpty())
		{
			System.out.println(mh.remove());
		}
	}
}
