package com.example.try_gameengine.framework;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator{
	Stack stack = new Stack();
	
	public CompositeIterator(Iterator iterator) {
		stack.push(iterator);
	}
	
//	public boolean hasNextByTheSameLevel(int Level) {
//		// TODO Auto-generated method stub
//		if(stack.empty()){
//			return false;
//		}else {
//			Iterator iterator = (Iterator) stack.peek();
//			ALayer layer = (ALayer) iterator.next();
//			layer.layerLevel
//			if(!iterator.hasNext()){
//				stack.pop();
//				return hasNext();
//			}else{
//				return true;
//			}
//		}
//	}
//	
//	public Object nextByTheSameLevel() {
//		// TODO Auto-generated method stub
//		if(hasNext()){
//			Iterator iterator = (Iterator) stack.peek();
//			ALayer layer = (ALayer) iterator.next();
//			stack.push(layer.createIterator());
//			return layer;
//		}else{
//			return null;
//		}	
//	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(stack.empty()){
			return false;
		}else {
			Iterator iterator = (Iterator) stack.peek();
			if(!iterator.hasNext()){
				stack.pop();
				return hasNext();
			}else{
				return true;
			}
		}
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		if(hasNext()){
			Iterator iterator = (Iterator) stack.peek();
			ALayer layer = (ALayer) iterator.next();
			stack.push(layer.createIterator());
			return layer;
		}else{
			return null;
		}	
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
