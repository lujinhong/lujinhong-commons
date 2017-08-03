import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/3/24 10:51
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION: 随机梯度下降的实现
 */
public class SGD {
    private static final Logger LOG = LoggerFactory.getLogger(SGD.class);
    private static final String SEPERATOR = " ";
    private static final int DIMENSION_SIZE = 5; //特征的维度
    private static double[] ws = new double[DIMENSION_SIZE]; //最终需要求得的w参数
    private static double[] zs = new double[DIMENSION_SIZE]; //保存上一个状态的w参数，需要根据这个来计算hx
    private static final double TRAINING_RATE = 0.000001; //必须要有学习率，否则会使得w数值变化非常快。
    private static final double L2 = 0.1;
    private static final double L1 = 1;

    /**
     * @param dataFilePath 文件中的一行内容如：1.9 2.0 2.0 6.其中最后一列为值，前面几列为标签对应的值。
     */
    public static void sgdAlgorithm(String dataFilePath) {
        try (Scanner sc = new Scanner(new File(dataFilePath))) {
            while (sc.hasNext()) {
                String dataLine = sc.nextLine();
                String[] dataContents = dataLine.split(SEPERATOR);
                for (int i = 0; i < DIMENSION_SIZE; i++) {
                    double yi = Double.parseDouble(dataContents[DIMENSION_SIZE]);//第一列表示这个样本的真实值
                    double hxi = 0.0; //计算使用当前的w所得到的预测结果
                    for (int j = 0; j < DIMENSION_SIZE; j++) {
                        hxi += ws[j] * Double.parseDouble(dataContents[j]);
                    }
                    zs[i] += TRAINING_RATE * (yi - hxi) * Double.parseDouble(dataContents[i]);
                }
                ws = zs.clone();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dataFilePath 文件中的一行内容如：1.9 2.0 2.0 6.其中最后一列为值，前面几列为标签对应的值。
     */
    public static void sgdAlgorithmWithL2(String dataFilePath) {
        try (Scanner sc = new Scanner(new File(dataFilePath))) {
            while (sc.hasNext()) {
                String dataLine = sc.nextLine();
                String[] dataContents = dataLine.split(SEPERATOR);
                for (int i = 0; i < DIMENSION_SIZE; i++) {
                    double yi = Double.parseDouble(dataContents[DIMENSION_SIZE]);//第一列表示这个样本的真实值
                    double hxi = 0.0; //计算使用当前的w所得到的预测结果
                    for (int j = 0; j < DIMENSION_SIZE; j++) {
                        hxi += ws[j] * Double.parseDouble(dataContents[j]);
                    }
                    zs[i] = zs[i] + TRAINING_RATE * ((yi - hxi) * Double.parseDouble(dataContents[i]) + L2 * zs[i]);
                }
                ws = zs.clone();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sgdAlgorithmWithL2("/Users/liaoliuqing/99_Project/lujinhong-commons/lujinhong-commons-ml/src/main/resources/linear_regression_training_data.txt");
        for (double w : ws) {
            System.out.println(w);
        }

        //生成学习数据
//        Random random = new Random(1);
//
//        for (int i = 0; i < 1000; i++) {
//            System.out.println((i / 3.0+random.nextDouble()*i * 0.01) + SEPERATOR + (i / 3.0+random.nextDouble()*i * 0.01) + SEPERATOR + (i / 3.0+random.nextDouble()*i * 0.01) + SEPERATOR + (i+random.nextDouble()*i*  0.01));
//        }
    }


}
