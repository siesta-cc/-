package suanfa1;
import java.lang.*;
public class Percolation {
    private boolean[] blocked;
    private WeightedQuickUnionUF map;
    private final int N;
    public Percolation(int N) throws IllegalArgumentException {// 创建一个N*N的所有节点都阻塞的图
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        map = new WeightedQuickUnionUF((N+2)*(N+2));
        blocked = new boolean[(N+2)*(N+2)];
        this.N = N;
        for (int i=0; i <=N+1; i++) {
            for(int j=0;j<=N+1;j++){
                blocked[i*(N+2)+j] = i>=1 && i<=N;
            }
        }
        for (int j=1;j<=N+1;j++){//将最上面和最下面两行的节点连接在一起
            _connect(0, j, 0, 0);
        }
        for (int j=1;j<=N+1;j++){
            _connect(N+1, j, N+1, 0);
        }
    }
    private void _connect(int i1, int j1, int i2, int j2) throws IndexOutOfBoundsException{
        if(!(i1>=0&&i1<=N+1&&j1>=0&&j1<=N+1)||!(i2>=0&&i2<=N+1&&j2>=0&&j2<=N+1)){
            throw new IndexOutOfBoundsException();
        }
        map.union(i1*(N+2)+j1, i2*(N+2)+j2);//每行n+2个数字
    }
    private boolean _isFull(int i, int j) throws IndexOutOfBoundsException{     // is site (row i, column j) full?
        if(!(i>=0&&i<=N+1&&j>=0&&j<=N+1)){
            throw new IndexOutOfBoundsException();
        }
        return map.connected(0, i*(N+2)+j);
    }
    private boolean _isOpen(int i, int j) throws IndexOutOfBoundsException{    // is site (row i, column j) open?
        if(!(i>=0&&i<=N+1&&j>=0&&j<=N+1)){
            throw new IndexOutOfBoundsException();
        }
        return !blocked[i*(N+2)+j];
    }
    public void open(int i, int j) throws IllegalArgumentException{// open site (row i, column j) if it is not already
        if(!(i>=1&&i<=N&&j>=1&&j<=N)){
            throw new IllegalArgumentException();
        }
        if(_isOpen(i-1,j))_connect(i, j, i-1, j);
        if(_isOpen(i+1,j))_connect(i, j, i+1, j);
        if(_isOpen(i,j-1))_connect(i, j, i, j-1);
        if(_isOpen(i,j+1))_connect(i, j, i, j+1);
        blocked[i*(N+2)+j] = false;
    }

    public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {    // is site (row i, column j) open?
        if (!(i >= 1 && i <= N && j >= 1 && j <= N)) {
            throw new IndexOutOfBoundsException();
        }
        return !blocked[i * (N + 2) + j];
    }
    public boolean isFull(int i, int j) throws IndexOutOfBoundsException{     // is site (row i, column j) full?
        if(!(i>=1&&i<=N&&j>=1&&j<=N)){
            throw new IndexOutOfBoundsException();
        }
        return map.connected(0, i*(N+2)+j);
    }
    public boolean percolates(){        // does the system percolate?
        return _isFull(N+1, 0);
    }

}
