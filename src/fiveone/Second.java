package fiveone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Second {



    public static final int SIX = 6* 60*60;
    public static final int EIGHT = 8*60*60;
    public static final int SIXTEEN = 16* 60*60;
    public static final int EIGHTTEEN = 18*60*60;
    public static final int TWENTY_THIRTY = 20*60*60 + 30*60;
    public static void main(String[] args) {



        LinkedList<Car> queue = new LinkedList<Car>();

        int carId = 1;
        int startTime = SIX;
        int endTime = TWENTY_THIRTY;
        int curTime = SIX;
        queue.add(new Car(carId++,SIX,getRestTime(SIX),getRestTime(SIX)));
        Car temp2 = queue.get(0);
        System.out.printf("%02d:%02d:%02d,%02d:%02d:%02d,%d,%02d:%02d:%02d,%d\n",(curTime/3600),curTime/60 % 60,
                curTime%60,temp2.endTime/3600,temp2.endTime/60%60,temp2.endTime%60,temp2.num,temp2.totalTime/3600,temp2.totalTime/60%60,
                temp2.totalTime%60,temp2.stopCount);

        for(int i = 0 ; i < queue.size();++i){

            queue.get(i).restTime -=getSubTime(SIX);
        }
        while(!queue.isEmpty()){

            if(check(curTime)){

                curTime = getCurTime(curTime);
            }
            else{

                curTime += getSubTime(curTime);
            }


            if(curTime > endTime){

                break;
            }

            Car temp = queue.get(0);
            Car temp1;

            int stopCount = 0;
            List<Car> cars = new ArrayList<Car>();
            for(int i = 0; i < queue.size(); ++i){

                if(queue.get(i).endTime < curTime){

                    ++stopCount;
                    cars.add(queue.get(i));
                }
            }

            if(temp.restTime <= 0 && temp.count < 10){

                temp1 = temp;
                queue.remove(0);
                temp1.totalTime += getRestTime(curTime);
                temp1.restTime = getRestTime(curTime);
                temp1.startTime = curTime;
                temp1.endTime = temp1.startTime + temp1.restTime;
                ++temp1.count;
                queue.add(temp1);

            }
            else{

                temp1 = new Car(carId++,curTime,getRestTime(curTime),getRestTime(curTime));

                queue.add(temp1);
            }



            temp1.stopCount = stopCount;

            Collections.sort(queue, new Comparator<Car>(){


                public int compare(Car o1,Car o2){


                    return o1.endTime - o2.endTime;
                }
            });
            System.out.printf("%02d:%02d:%02d,%02d:%02d:%02d,%d,%02d:%02d:%02d,%d,",(curTime/3600),curTime/60 % 60,
                    curTime%60,temp1.endTime/3600,temp1.endTime/60%60,temp1.endTime%60,temp1.num,temp1.totalTime/3600,temp1.totalTime/60%60,
                    temp1.totalTime%60,temp1.stopCount);

            for(int i = 0 ; i < cars.size(); ++i){

                System.out.printf("%d ", cars.get(i).num);
            }
            System.out.println();
            for(int i = 0 ; i < queue.size();++i){

                queue.get(i).restTime -=getSubTime(curTime);
            }
        }


    }

    public static int getCurTime(int curTime){

        if(curTime > EIGHTTEEN){

            return EIGHTTEEN;
        }
        else if(curTime > SIXTEEN){

            return SIXTEEN;
        }
        else if(curTime > EIGHT){

            return EIGHT;
        }

        return SIX;
    }
    public static boolean check(int curTime){


        if(getSubTime(curTime + getSubTime(curTime)) != getSubTime(curTime) && isInt(curTime)){

            return true;
        }

        return false;

    }

    public static boolean isInt(int curTime){

        if(curTime == SIX || curTime == EIGHT || curTime == SIXTEEN || curTime == EIGHTTEEN || curTime == TWENTY_THIRTY){

            return true;
        }
        return false;
    }


    public static int getSubTime(int curTime){


        if(curTime >= SIX && curTime < EIGHT || curTime >= SIXTEEN && curTime < EIGHTTEEN){

            return 4*60;
        }
        else if(curTime >= EIGHT && curTime < SIXTEEN){

            return 7*60;

        }
        else{

            return 4*60+30;
        }
    }
    public static int getRestTime(int curTime){

        if(curTime >= SIX && curTime < EIGHT || curTime >= SIXTEEN && curTime < EIGHTTEEN){

            return 80*60;
        }
        else if(curTime >= EIGHT && curTime < SIXTEEN){

            return 70*60;
        }
        else{

            return 75*60;
        }
    }

    public static class Car{

        int num;
        int startTime;
        int endTime;
        int restTime;
        int totalTime;
        int count;
        int kind;
        int stopCount;
        public Car(int num,int startTime,int restTime,int totalTime){

            this.num = num;
            this.startTime = startTime;
            this.restTime = restTime;
            this.totalTime = totalTime;
            this.endTime = this.startTime + restTime;
            count = 0;
            kind = 0;
            stopCount = 0;
        }
    }
}
