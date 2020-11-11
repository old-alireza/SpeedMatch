package alireza.sn.matchspeed;

class MyImage {
    private int color;
    private int image;

    public MyImage(int image , int color) {
        this.image = image;
        this.color = color;
    }

    public int getColor (){
        return this.color;
    }

    public int getImage (){
        return this.image;
    }
}