/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/7/28 10:46
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:
 */
public class LRTest {
    private static final String sample1 = "ID A:1 B:1";
    private static final String sample2 = "ID B:1";
    private static final String[] samples = {sample1, sample2, sample2, sample2, sample2, sample2, sample2, sample2, sample2, sample2};
    private static final double eta = 0.001; //学习率


    public static void main(String[] args) {
        ClassicalLR();
        attenuationLearningRate();
        accGradientLearningRate();
        accGradientAndImbalance();
    }

    //经典的SGD。w = w - eta*g = w - eta*(p-y)*x
    private static void ClassicalLR() {
        double wA = 0.0; //标签A对应的w
        double wB = 0.0; //标签B对应的w
        int value = 1;
        for (int i = 1; i <= 100; i++) {
            int label = i % 10 == 0 ? 1 : 0; //每10个样本一个正样本。
            for (int j = 0; j < samples.length; j++) {
                double p = 1 / (1 + Math.exp(wA + wB));
                if (samples[j].equals(sample1)) {
                    wA = wA - eta * (p - label) * value;
                    wB = wB - eta * (p - label) * value;

                } else {
                    wB = wB - eta * (p - label);
                }
            }
        }
        System.out.println(wA + "\t" + wB);
    }

    //学习率一直衰减。w = w - eta*g/f(t) = w - eta*(p-y)*x/f(t)，f(t)是衰减函数，最经典的方式是使用t的开方。
    private static void attenuationLearningRate() {
        double wA = 0.0; //标签A对应的w
        double wB = 0.0; //标签B对应的w
        int countA = 1; //A标签出现的次数
        int countB = 1;//B标签出现的次数
        int value = 1;
        for (int i = 1; i <= 100; i++) {
            int label = i % 10 == 0 ? 1 : 0; //每10个样本一个正样本。
            for (int j = 0; j < samples.length; j++) {
                double p = 1 / (1 + Math.exp(wA + wB));
                if (samples[j].equals(sample1)) {
                    wA = wA - eta * (p - label) * value / Math.sqrt(countA);
                    wB = wB - eta * (p - label) * value / Math.sqrt(countB);
                    ++countA;
                    ++countB;
                } else {
                    wB = wB - eta * (p - label) / Math.sqrt(countB);
                    ++countB;
                }
            }
        }

        System.out.println(wA + "\t" + wB);
    }

    //累积梯度学习率
    private static void accGradientLearningRate() {
        double wA = 0.0; //标签A对应的w
        double wB = 0.0; //标签B对应的w
        double wg1 = 1.0; //A标签出现的次数
        double wg2 = 1.0;//B标签出现的次数
        int value = 1;
        for (int i = 1; i <= 100; i++) {
            int label = i % 10 == 0 ? 1 : 0; //每10个样本一个正样本。
            for (int j = 0; j < samples.length; j++) {
                double p = 1 / (1 + Math.exp(wA + wB));
                if (samples[j].equals(sample1)) {
                    wA = wA - eta * (p - label) * value / Math.sqrt(wg1);
                    wB = wB - eta * (p - label) * value / Math.sqrt(wg2);
                    wg1 += (p - label);
                    wg2 += (p - label);
                } else {
                    wB = wB - eta * (p - label) / Math.sqrt(wg2);
                    wg2 += (p - label);
                }
            }
        }
        System.out.println(wA + "\t" + wB);
        System.out.println(wg1 + "\t" + wg2);
    }

    //累积梯度学习率 & imbalance
    private static void accGradientAndImbalance() {
        double wA = 0.0; //标签A对应的w
        double wB = 0.0; //标签B对应的w
        double wg1 = 1.0; //A标签出现的次数
        double wg2 = 1.0;//B标签出现的次数
        int value = 1;
        for (int i = 1; i <= 100; i++) {
            int label = i % 10 == 0 ? 1 : 0; //每10个样本一个正样本。
            for (int j = 0; j < samples.length; j++) {
                double p = 1 / (1 + Math.exp(wA + wB));
                int imbalanceFactor = (p - label > 0) ? 9 : 1; //如果是负样本，则平衡因子设置为9，即负正样本的比率；
                if (samples[j].equals(sample1)) {
                    wA = wA - eta * (p - label) * value / (Math.sqrt(wg1) * imbalanceFactor);
                    wB = wB - eta * (p - label) * value / (Math.sqrt(wg2) * imbalanceFactor);
                    wg1 += (p - label);
                    wg2 += (p - label);
                } else {
                    wB = wB - eta * (p - label) / (Math.sqrt(wg2) * imbalanceFactor);
                    wg2 += (p - label);
                }
            }
        }

        System.out.println(wA + "\t" + wB);
        System.out.println(wg1 + "\t" + wg2);
    }
}
