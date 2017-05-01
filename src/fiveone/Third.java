package fiveone;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by 36249 on 2017/5/1.
 */
public class Third {


    public static final int SIX = 6 * 60 * 60;
    public static final int EIGHT = 8 * 60 * 60;
    public static final int SIXTEEN = 16 * 60 * 60;
    public static final int EIGHTTEEN = 18 * 60 * 60;
    public static final int TWENTY_THIRTY = 20 * 60 * 60 + 30 * 60;

    public static void main(String[] args) throws Exception {

        FileOutputStream fw = new FileOutputStream("C:\\Documents and Settings\\Administrator\\桌面\\1.csv");
        PrintStream p = new PrintStream(fw);
        System.setOut(p);
        LinkedList<Car> queue = new LinkedList<Car>();
        LinkedList<Car> oddList = new LinkedList<Car>();
        int carId = 1;
        int startTime = SIX;
        int endTime = TWENTY_THIRTY;
        int curTime = SIX;

        Car first = new Car(carId++, SIX, getRestTime(SIX), getRestTime(SIX));
        first.kind = 1;
        queue.add(first);
        Car temp2 = queue.get(0);
        System.out.printf("%02d:%02d:%02d,%02d:%02d:%02d,%d,%02d:%02d:%02d,%d,,%d\n", (curTime / 3600), curTime / 60 % 60,
                curTime % 60, temp2.endTime / 3600, temp2.endTime / 60 % 60, temp2.endTime % 60, temp2.num, temp2.totalTime / 3600, temp2.totalTime / 60 % 60,
                temp2.totalTime % 60, temp2.stopCount, temp2.kind);

        for (int i = 0; i < queue.size(); ++i) {

            queue.get(i).restTime -= getSubTime(SIX);
        }

        while (!queue.isEmpty()) {

            if (check(curTime)) {

                curTime = getCurTime(curTime);
            } else {

                curTime += getSubTime(curTime);
            }


            if (curTime > endTime) {

                break;
            }

            Car temp = queue.get(0);
            Car temp1;

            int stopCount = 0;
            List<Car> cars = new ArrayList<Car>();

            //找在站等待的车
            for (int i = 0; i < queue.size(); ++i) {

                if (queue.get(i).endTime < curTime) {

                    ++stopCount;
                    cars.add(queue.get(i));
                }
            }


            for (int i = 0; i < queue.size(); ++i) {


                if (queue.get(i).kind == 1 && curTime >= EIGHT && queue.get(i).startTime < EIGHT) {


                    oddList.add(queue.get(i));
                    queue.remove(queue.get(i));

                    --i;

                }

            }

            if (curTime >= SIXTEEN && curTime < SIXTEEN + 40 * 60 && oddList.size() > 0) {


                temp1 = oddList.getFirst();
                oddList.remove(temp1);
                temp1.endTime = curTime + getRestTime(curTime);
                temp1.restTime = getRestTime(curTime);
                temp1.startTime = curTime;
                temp1.totalTime += getRestTime(curTime);
                ++temp1.count;
                queue.add(temp1);

            } else if (temp.restTime <= 0 && temp.count < 10) {

                temp1 = temp;
                queue.remove(0);
                temp1.totalTime += getRestTime(curTime);
                temp1.restTime = getRestTime(curTime);
                temp1.startTime = curTime;
                temp1.endTime = temp1.startTime + temp1.restTime;
                ++temp1.count;

                queue.add(temp1);

            } else {

                temp1 = new Car(carId++, curTime, getRestTime(curTime), getRestTime(curTime));
                if (temp1.num < 4) {

                    temp1.kind = 1;
                }
                queue.add(temp1);
            }


            temp1.stopCount = stopCount;

            Collections.sort(queue, new Comparator<Car>() {


                public int compare(Car o1, Car o2) {


                    return o1.endTime - o2.endTime;
                }
            });
            System.out.printf("%02d:%02d:%02d,%02d:%02d:%02d,%d,%02d:%02d:%02d,%d,", (curTime / 3600), curTime / 60 % 60,
                    curTime % 60, temp1.endTime / 3600, temp1.endTime / 60 % 60, temp1.endTime % 60, temp1.num, temp1.totalTime / 3600, temp1.totalTime / 60 % 60,
                    temp1.totalTime % 60, temp1.stopCount);

            for (int i = 0; i < cars.size(); ++i) {

                System.out.printf("%d ", cars.get(i).num);
            }
            System.out.printf(",%d", temp1.kind);

            System.out.println();
            for (int i = 0; i < queue.size(); ++i) {

                queue.get(i).restTime -= getSubTime(curTime);
            }
        }


    }


    public static int getCurTime(int curTime) {

        if (curTime > EIGHTTEEN) {

            return EIGHTTEEN;
        } else if (curTime > SIXTEEN) {

            return SIXTEEN;
        } else if (curTime > EIGHT) {

            return EIGHT;
        }

        return SIX;
    }

    public static boolean check(int curTime) {


        if (getSubTime(curTime + getSubTime(curTime)) != getSubTime(curTime) && isInt(curTime)) {

            return true;
        }

        return false;

    }

    public static boolean isInt(int curTime) {

        if (curTime == SIX || curTime == EIGHT || curTime == SIXTEEN || curTime == EIGHTTEEN || curTime == TWENTY_THIRTY) {

            return true;
        }
        return false;
    }


    public static int getSubTime(int curTime) {


        if (curTime >= SIX && curTime < EIGHT || curTime >= SIXTEEN && curTime < EIGHTTEEN) {

            return 4 * 60;
        } else if (curTime >= EIGHT && curTime < SIXTEEN) {

            return 7 * 60;

        } else {

            return 4 * 60 + 30;
        }
    }

    public static int getRestTime(int curTime) {

        if (curTime >= SIX && curTime < EIGHT || curTime >= SIXTEEN && curTime < EIGHTTEEN) {

            return 80 * 60;
        } else if (curTime >= EIGHT && curTime < SIXTEEN) {

            return 70 * 60;
        } else {

            return 75 * 60;
        }
    }

    public static class Car {

        int num;
        int startTime;
        int endTime;
        int restTime;
        int totalTime;
        int count;
        int kind;
        int stopCount;

        public Car(int num, int startTime, int restTime, int totalTime) {

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
