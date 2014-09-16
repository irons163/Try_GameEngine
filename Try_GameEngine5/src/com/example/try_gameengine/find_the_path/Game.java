package com.example.try_gameengine.find_the_path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import android.annotation.SuppressLint;
import android.os.Handler;//引入相關類別
import android.os.Message;//引入相關類別
import android.util.Log;
import android.widget.Button;//引入相關類別
import android.widget.TextView;//引入相關類別

public class Game {//演算法類別
	int algorithmId=0;//演算法代號 0--深度優先
	int mapId = 0;//地圖編號
	int[][] map = MapList.map[mapId];
	int[] source = MapList.source;//出發點
	int[] target = MapList.target[0];//目標點
//	GameView gameView;//gameView的引用
	Button goButton;//goButton的引用
	TextView BSTextView;//BSTextView的引用
	ArrayList<int[][]> searchProcess=new ArrayList<int[][]>();//搜索過程
	Stack<int[][]> stack=new Stack<int[][]>();//深度優先所用堆疊
	HashMap<String,int[][]> hm=new HashMap<String,int[][]>();//結果路徑記錄
	LinkedList<int[][]> queue=new LinkedList<int[][]>();//廣度優先所用佇列
	//A*用優先順序佇列
	PriorityQueue<int[][]> astarQueue=new PriorityQueue<int[][]>(100,new AStarComparator(this));
	//記錄到每個點的最短路徑 for Dijkstra
	HashMap<String,ArrayList<int[][]>> hmPath=new HashMap<String,ArrayList<int[][]>>();
	//記錄路徑長度 for Dijkstra
	int[][] length=new int[MapList.map[mapId].length][MapList.map[mapId][0].length];
	int[][] visited=new int[MapList.map[0].length][MapList.map[0][0].length];//0 未去過 1 去過
	int[][] sequence={
		{0,1},{0,-1},
		{-1,0},{1,0},
		{-1,1},{-1,-1},
		{1,-1},{1,1}
	};
	boolean pathFlag=false;//true 找到了路徑
	int timeSpan=10;//時間間隔
	private Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			Log.e("stopFind", "stopFind");
		};
	};
	
	FindThePath findThePath;
	
//	private Handler myHandler = new Handler(){//用來更新UI執行緒的控制項
////        @SuppressLint("HandlerLeak")
//		public void handleMessage(Message msg){
//        	Log.e("stopFind", "stopFind");
//        	if(msg.what == 1){//改變按鈕狀態
////        		goButton.setEnabled(true);
////        		synchronized (FindThePath.class) {
////        			FindThePath.class.notifyAll();
////        		}
//        	}
//        	else if(msg.what == 2){//改變步數的TextView的值
////        		BSTextView.setText("使用步數：" + (Integer)msg.obj);
////        		synchronized (FindThePath.class) {
////        			FindThePath.class.notifyAll();
////        		}
//        	}
//        }
//	};
	public void clearState(){//清空所有狀態與清單
		pathFlag=false;	
		searchProcess.clear();
		stack.clear();
		queue.clear();
		astarQueue.clear();
		hm.clear();
		visited=new int[MapList.map[mapId].length][MapList.map[mapId][0].length];
		hmPath.clear();
		for(int i=0;i<length.length;i++){
			for(int j=0;j<length[0].length;j++){
				length[i][j]=9999;//初始路徑長度為最大距離都不可能的那麼大	
			}
		}
	}
	public void runAlgorithm(){//運行演算法
		clearState();
		switch(algorithmId){
			case 0://深度優先演算法
				DFS();
				break;
			case 1://廣度優先演算法
				BFS();
				break;
			case 2://廣度優先 A*演算法
				BFSAStar();
				break;				
			case 3://Dijkstra演算法
				Dijkstra();
				break;
			case 4:
				DijkstraAStar();//DijkstraA*演算法
				break;				
		}		
	}
	

	public void DFS(){//深度優先演算法
		new Thread(){
			public void run(){
				boolean flag=true;
				int[][] start={//開始狀態
					{source[0],source[1]},
					{source[0],source[1]}
				};
				stack.push(start);
				int count=0;//步數計數器
				while(flag){
					int[][] currentEdge=stack.pop();//從堆疊頂取出邊
					int[] tempTarget=currentEdge[1];//取出此邊的目的點
					//判斷目的點是否去過，若去過則直接進入下次迴圈
					if(visited[tempTarget[1]][tempTarget[0]]==1){
						continue;
					}
					count++;
					visited[tempTarget[1]][tempTarget[0]]=1;//標識目的點為訪問過
					//將臨時目的點加入搜索過程中
					searchProcess.add(currentEdge);
					//記錄此臨時目的點的父節點
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
//					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//判斷有否找到目的點
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					//將所有可能的邊入堆疊
					int currCol=tempTarget[0];
					int currRow=tempTarget[1];
					for(int[] rc:sequence){
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<MapList.map[mapId].length&&currCol+j>=0&&currCol+j<MapList.map[mapId][0].length&&
						map[currRow+i][currCol+j]!=1){
							int[][] tempEdge={
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							stack.push(tempEdge);
						}
					}
				}
				pathFlag=true;	
//				gameView.postInvalidate();
				//設定按鈕的可用性
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);
				myHandler.sendEmptyMessage(1);
				//改變TextView文字
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);
				
				synchronized (findThePath) {
					findThePath.notifyAll();
	    		}
			}
		}.start();		
	}
	
	public void BFS(){//廣度優先演算法
		new Thread(){
			public void run(){
				int count=0;//步數計數器
				boolean flag=true;
				int[][] start={//開始狀態
					{source[0],source[1]},
					{source[0],source[1]}
				};
				queue.offer(start);
				while(flag){					
					int[][] currentEdge=queue.poll();//從隊首取出邊
					int[] tempTarget=currentEdge[1];//取出此邊的目的點
					//判斷目的點是否去過，若去過則直接進入下次迴圈
					if(visited[tempTarget[1]][tempTarget[0]]==1){
						continue;
					}
					count++;
					visited[tempTarget[1]][tempTarget[0]]=1;//標識目的點為訪問過
					searchProcess.add(currentEdge);//將臨時目的點加入搜索過程中
					//記錄此臨時目的點的父節點
					hm.put(tempTarget[0]+":"+tempTarget[1],
							new int[][]{currentEdge[1],currentEdge[0]});
//					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//判斷有否找到目的點
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						Log.e("break", "break");
						break;
					}
					//將所有可能的邊入佇列
					int currCol=tempTarget[0];
					int currRow=tempTarget[1];
					for(int[] rc:sequence){
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<MapList.map[mapId].length
								&&currCol+j>=0&&currCol+j<MapList.map[mapId][0].length&&
						map[currRow+i][currCol+j]!=1){
							int[][] tempEdge={
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							queue.offer(tempEdge);
						}
					}
				}
				pathFlag=true;	
//				gameView.postInvalidate();
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);//設定按鈕的可用性
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);//改變TextView文字
				
				synchronized (findThePath) {
					findThePath.notifyAll();
	    		}
			}
		}.start();				
	}
	
	public void Dijkstra(){//Dijkstra演算法
		new Thread(){
			public void run(){
				int count=0;//步數計數器
				boolean flag=true;//搜索迴圈控制
				int[] start={source[0],source[1]};//開始點col,row	
				visited[source[1]][source[0]]=1;
				for(int[] rowcol:sequence){	//計算此點所有可以到達點的路徑及長度				
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>18||tcol<0||tcol>18)continue;
					if(map[trow][tcol]!=0)continue;
					//記錄路徑長度
					length[trow][tcol]=1;
					//計算路徑					
					String key=tcol+":"+trow;
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					//將去過的點記錄			
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					count++;			
				}				
//				gameView.postInvalidate();
				outer:while(flag){					
					//找到當前擴展點K 要求擴展點K為從開始點到此點目前路徑最短，且此點未考察過
					int[] k=new int[2];
					int minLen=9999;
					for(int i=0;i<visited.length;i++){
						for(int j=0;j<visited[0].length;j++){
							if(visited[i][j]==0){
								if(minLen>length[i][j]){
									minLen=length[i][j];
									k[0]=j;//col
									k[1]=i;//row
								}
							}
						}
					}
					visited[k[1]][k[0]]=1;//設定去過的點					
//					gameView.postInvalidate();//重繪
					int dk=length[k[1]][k[0]];//取出開始點到K的路徑長度
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);//取出開始點到K的路徑
					//迴圈計算所有K點能直接到的點到開始點的路徑長度
					for(int[] rowcol:sequence){
						int trow=k[1]+rowcol[1];//計算出新的要計算的點的座標
						int tcol=k[0]+rowcol[0];
						//若要計算的點超出地圖邊界或地圖上此位置為障礙物則捨棄考察此點
						if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
						if(map[trow][tcol]!=0)continue;
						int dj=length[trow][tcol];//取出開始點到此點的路徑長度
						int dkPluskj=dk+1;//計算經K點到此點的路徑長度
						//若經K點到此點的路徑長度比原來的小則修改到此點的路徑
						if(dj>dkPluskj){
							String key=tcol+":"+trow;
							//克隆開始點到K的路徑
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							//將路徑中加上一步從K到此點
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							//將此路徑設定為從開始點到此點的路徑
							hmPath.put(key,tempal);
							//修改到從開始點到此點的路徑長度							
							length[trow][tcol]=dkPluskj;
							//若此點從未計算過路徑長度則將此點加入考察過程記錄
							if(dj==9999){//將去過的點記錄	
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});
								count++;
							}
						}
						//看是否找到目的點
						if(tcol==target[0]&&trow==target[1]){
							pathFlag=true;
							Message msg1 = myHandler.obtainMessage(1);
							myHandler.sendMessage(msg1);//設定按鈕的可用性
							Message msg2 = myHandler.obtainMessage(2, count);
							myHandler.sendMessage(msg2);//改變TextView文字
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}	

	public void BFSAStar(){//廣度優先 A*演算法
		new Thread(){
			public void run(){
				boolean flag=true;
				int[][] start={//開始狀態
					{source[0],source[1]},
					{source[0],source[1]}
				};
				astarQueue.offer(start);
				int count=0;
				while(flag){	
					int[][] currentEdge;
					int[] tempTarget;
					try {
						currentEdge = astarQueue.poll();//從隊首取出邊
						tempTarget=currentEdge[1];//取出此邊的目的點
					} catch (Exception e) {
						// TODO: handle exception
						hm = null;
						break;
					}
					
					//判斷目的點是否去過，若去過則直接進入下次迴圈
					if(visited[tempTarget[1]][tempTarget[0]]!=0){
						continue;
					}
					count++;
					//標識目的點為訪問過
					visited[tempTarget[1]][tempTarget[0]]=visited[currentEdge[0][1]][currentEdge[0][0]]+1;				
					searchProcess.add(currentEdge);//將臨時目的點加入搜索過程中
					//記錄此臨時目的點的父節點
					hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});
//					gameView.postInvalidate();
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}
					//判斷有否找到目的點
					if(tempTarget[0]==target[0]&&tempTarget[1]==target[1]){
						break;
					}
					int currCol=tempTarget[0];//將所有可能的邊入優先順序佇列
					int currRow=tempTarget[1];
					for(int[] rc:sequence){
						int i=rc[1];
						int j=rc[0];
						if(i==0&&j==0){continue;}
						if(currRow+i>=0&&currRow+i<MapList.map[mapId].length&&currCol+j>=0
								&&currCol+j<MapList.map[mapId][0].length&&
						map[currRow+i][currCol+j]!=1){
							int[][] tempEdge={
								{tempTarget[0],tempTarget[1]},
								{currCol+j,currRow+i}
							};
							astarQueue.offer(tempEdge);
						}						
					}
				}
				pathFlag=true;	
//				gameView.postInvalidate();
				Message msg1 = myHandler.obtainMessage(1);
				myHandler.sendMessage(msg1);//設定按鈕的可用性
				Message msg2 = myHandler.obtainMessage(2, count);
				myHandler.sendMessage(msg2);//改變TextView文字
				
				synchronized (findThePath) {
					findThePath.notifyAll();
	    		}
			}
		}.start();				
	}

	public void DijkstraAStar(){//Dijkstra A*演算法
		new Thread(){
			public void run(){
				int count=0;//步數計數器
				boolean flag=true;//搜索迴圈控制
				int[] start={source[0],source[1]};//開始點col,row	
				visited[source[1]][source[0]]=1;
				//計算此點所有可以到達點的路徑及長度
				for(int[] rowcol:sequence){					
					int trow=start[1]+rowcol[1];
					int tcol=start[0]+rowcol[0];
					if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
					if(map[trow][tcol]!=0)continue;
					//記錄路徑長度
					length[trow][tcol]=1;
					String key=tcol+":"+trow;//計算路徑
					ArrayList<int[][]> al=new ArrayList<int[][]>();
					al.add(new int[][]{{start[0],start[1]},{tcol,trow}});
					hmPath.put(key,al);	
					//將去過的點記錄			
					searchProcess.add(new int[][]{{start[0],start[1]},{tcol,trow}});					
					count++;			
				}				
//				gameView.postInvalidate();
				outer:while(flag){					
					int[] k=new int[2];
					int minLen=9999;
					boolean iniFlag=true;
					for(int i=0;i<visited.length;i++){
						for(int j=0;j<visited[0].length;j++){
							if(visited[i][j]==0){
								//與普通Dijkstra演算法的區別部分=========begin=================================
								if(length[i][j]!=9999){
									if(iniFlag){//第一個找到的可能點
										minLen=length[i][j]+
										(int)Math.sqrt((j-target[0])*(j-target[0])+(i-target[1])*(i-target[1]));
										k[0]=j;//col
										k[1]=i;//row
										iniFlag=!iniFlag;
									}
									else{
										int tempLen=length[i][j]+
										(int)Math.sqrt((j-target[0])*(j-target[0])+(i-target[1])*(i-target[1]));
										if(minLen>tempLen){
											minLen=tempLen;
											k[0]=j;//col
											k[1]=i;//row
										}
									}
								}
								//與普通Dijkstra演算法的區別部分==========end==================================
							}
						}
					}
					//設定去過的點
					visited[k[1]][k[0]]=1;					
					//重繪					
//					gameView.postInvalidate();
					int dk=length[k[1]][k[0]];
					ArrayList<int[][]> al=hmPath.get(k[0]+":"+k[1]);
					for(int[] rowcol:sequence){
						int trow=k[1]+rowcol[1];
						int tcol=k[0]+rowcol[0];
						if(trow<0||trow>MapList.map[mapId].length-1||tcol<0||tcol>MapList.map[mapId][0].length-1)continue;
						if(map[trow][tcol]!=0)continue;
						int dj=length[trow][tcol];						
						int dkPluskj=dk+1;
						if(dj>dkPluskj){
							String key=tcol+":"+trow;
							ArrayList<int[][]> tempal=(ArrayList<int[][]>)al.clone();
							tempal.add(new int[][]{{k[0],k[1]},{tcol,trow}});
							hmPath.put(key,tempal);							
							length[trow][tcol]=dkPluskj;
							if(dj==9999){
								//將去過的點記錄			
								searchProcess.add(new int[][]{{k[0],k[1]},{tcol,trow}});								
								count++;
							}
						}
						//看是否找到目的點
						if(tcol==target[0]&&trow==target[1]){
							pathFlag=true;
							Message msg1 = myHandler.obtainMessage(1);
							myHandler.sendMessage(msg1);//設定按鈕的可用性
							Message msg2 = myHandler.obtainMessage(2, count);
							myHandler.sendMessage(msg2);//改變TextView文字
							break outer;
						}
					}										
					try{Thread.sleep(timeSpan);}catch(Exception e){e.printStackTrace();}				
				}								
			}
		}.start();					
	}
}
