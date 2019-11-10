package cooperation;
/**
 * 生产者消费者模式：管程法
 * @author dell
 *
 */

public class CoTest01 {
	public static void main(String[] args) {
		SynCotainer synCotainer=new SynCotainer(10);
		new Productor(synCotainer).start();
		new Consumer(synCotainer).start();
	}

}

//生产者
class Productor extends Thread{
	private SynCotainer synCotainer;
	
	public Productor(SynCotainer synCotainer) {
		super();
		this.synCotainer = synCotainer;
	}

	public void run() {
		for(int i=1;i<50;i++) {
			this.synCotainer.push(new Steamedbun(i));
			System.out.println("生产第"+i+"个商品");
		}
	}
}

//消费者
class Consumer extends Thread{
	private SynCotainer synCotainer;
	
	public Consumer(SynCotainer synCotainer) {
		super();
		this.synCotainer = synCotainer;
	}

	public void run() {
		for(int i=1;i<50;i++) {
			this.synCotainer.pop();
			System.out.println("消费第"+i+"个商品");
		}
	}
	
}

//缓冲区
class SynCotainer{
	Steamedbun[] steamedbun;
	int count=0;
	public SynCotainer(int n) {
		super();
		this.steamedbun=new Steamedbun[n];
	}
	//存放
	public synchronized void push(Steamedbun s) {
		if(count==this.steamedbun.length) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		this.steamedbun[count++]=s;
		this.notifyAll();
	}
	//获取
	public synchronized Steamedbun pop() {
		if(count==0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		Steamedbun s=steamedbun[--count];
		this.notifyAll();
		return s;
	}
}
//商品
class Steamedbun{
	private int id;

	public Steamedbun(int id) {
		super();
		this.id = id;
	}
	
	
}