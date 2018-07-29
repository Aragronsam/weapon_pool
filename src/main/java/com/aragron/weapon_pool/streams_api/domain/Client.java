package com.aragron.weapon_pool.streams_api.domain;

import com.aragron.weapon_pool.streams_api.domain.domain.WinnerGuess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能描述: Streams初探
 *
 * @Author: aragron
 * @Create: 2018/7/29-12:29
 * @Home: http://aragron.com
 */
public class Client {
    static List<WinnerGuess> init() {
        return Arrays.asList(
                new WinnerGuess("Konie", "13122631111", 2, 11, "杭州", "阿根廷", "梅西"),
                new WinnerGuess("Alex", "13122632222", 2, 12, "上海", "法国", "凯恩"),
                new WinnerGuess("Aragron", "13122633333", 1, 13, "上海", "德国", "梅西"),
                new WinnerGuess("Rachel", "13122634444", 2, 14, "深圳", "法国", "C罗"),
                new WinnerGuess("Lexi", "13122635555", 2, 20, "武汉", "法国", "齐达内"),
                new WinnerGuess("Mike", "13122636666", 1, 19, "武汉", "巴西", "C罗"),
                new WinnerGuess("May", "13122637777", 2, 18, "深圳", "法国", "梅西"),
                new WinnerGuess("Fairy", "13122638888", 2, 17, "武汉", "西班牙", "梅西"),
                new WinnerGuess("Emily", "13122639999", 2, 16, "上海", "法国", "C罗"),
                new WinnerGuess("Kevin", "13122630000", 1, 15, "上海", "德国", "梅西"),
                new WinnerGuess("Merry", "13122630001", 2, 10, "泰国", "俄罗斯", "梅西")
        );
    }

    public static void main(String[] args) {
        //准备竞猜记录数据(虚构)⚽️【前段时间的世界杯太火了，所以业务场景为期间的竞猜(业务好理解)，主要为了说明Streams】
        List<WinnerGuess> winnerGuesses = init();

        /**需求一：竞猜最佳射手为梅西(bestPlayer为梅西)的用户有哪些*/
        List<String> names_guessed_Messi = winnerGuesses.stream()//转换为stream
                .filter(winnerGuess -> "梅西".equals(winnerGuess.getBestPlayer()))//过滤，最佳射手为梅西🧔
                .map(winnerGuess -> {//转换，选择一
                    return winnerGuess.getName();//获取竞猜用户的姓名
                })
                //.map(winnerGuess -> winnerGuess.getName())//对上面的简写
                //.map(WinnerGuess::getName)//选择二(涉及到方法引用)，获取竞猜用户的姓名
                .collect(Collectors.toList());//汇总，将过滤后的用户姓名汇总，返回为一个list
        System.out.println("竞猜最佳射手为梅西的用户有：" + names_guessed_Messi);

        /**需求二：竞猜最佳射手为C罗(bestPlayer为C罗)的用户有几个*/
        long count_guessed_C = winnerGuesses.stream()//转换为stream
                .filter(winnerGuess -> "C罗".equals(winnerGuess.getBestPlayer()))//过滤，最佳射手为C罗👶
                .count();//汇总，将过滤后的用户求和，返回个数
        System.out.println("竞猜最佳射手为C罗的用户有" + count_guessed_C + "个");

        /**需求三：是否有来自泰国的竞猜者*/
        boolean hasUserThailand = winnerGuesses.parallelStream().anyMatch(winnerGuess -> "泰国".equals(winnerGuess.getCity()));
        System.out.println(hasUserThailand);

        /**需求四：给性别为女(sex等于2)且来自于上海(city为深圳)且竞猜的冠军球队为法国(winnerTeam为法国)的用户每人发一条短信，
         短信内容为：Hi，xxx，恭喜你竞猜冠军成功，100w奖金将会在你xx(当前年龄加1)岁生日🎂，打到你的账户，到时请注意查收！*/
        winnerGuesses.stream()//转换为stream
                .filter(winnerGuess -> "深圳".equals(winnerGuess.getCity()))//过滤，来自深圳🌈
                .filter(winnerGuess -> winnerGuess.getSex().equals(2))//过滤，女孩👩
                .map(winnerGuess -> {//转换
                    winnerGuess.setAge(winnerGuess.getAge() + 1);//年龄加1
                    return winnerGuess;
                })
                .sorted(Comparator.comparing(WinnerGuess::getAge).reversed())//排序，年龄升序
                .limit(2)//前2位竞猜者
                .parallel()//转换为并行stream(并行操作，充分利用多核cpu效能)
                .forEach(winnerGuess -> sendMobileMsg(winnerGuess));//给每个用户发送短信
        /**
         1.这个需求是streams api【jkd8才能】的一种典型用法，自己工作中经常也这样用
         2.想想如果是jkd8之前要写这样的逻辑，要写多少for循环，多少if，多少无意义的中间变量，然而有了streams api，一行代码就搞定了，而且更简洁，表达性更强
         3.💣💣💣💣不过需要自己换好行💣💣💣💣，不然看的时候也是头大🐶🐶🐶
         4.其实filter-map-reduce是现在先行计算机行业通用的思想，然后结合lambda表达式和函数式编程(不在本文的讨论之列，不过待会可以给出相应的资料参考链接🔗)
         使用后，你会觉得编程又美好了一些。
         ⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩⛩*/

        /**
         * 对stream操作类型，有三种：
         *      1.Intermediate(英文意思为，中间)
         *          filter、map、distinct、sorted、peek、limit、skip、parallel、sequential、unordered，
         *          【引用】【🔗见文末】一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，
         *           交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
         *      2.Terminal
         *          forEach、forEachOrdered、toArray、reduce、collect、min、max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator
         *          【🔗】一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
         *           Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
         *      3.short-circuiting
         *          anyMatch、allMatch、noneMatch、findFirst、findAny、limit
         *          【🔗】用以指：
         *           对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
         *           对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
         *
         * 【❗🔗】注意：
         *      在对于一个 Stream 进行多次转换操作 (Intermediate 操作)，每次都对 Stream 的每个元素进行转换，而且是执行多次，
         *      这样时间复杂度就是 N（转换次数）个 for 循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是 lazy 的，
         *      多个转换操作只会在 Terminal 操作的时候融合起来，一次循环完成。我们可以这样简单的理解，Stream 里有个操作函数的集合，
         *      每次转换操作就是把转换函数放入这个集合中，在 Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。
         */

        /**
         * 至于为什么没常规去介绍先什么是stream，什么是lambda啥的，是因为网上有好多好文章【🔗】已经解释清楚了，不必累述
         * 相信聪明的你看看应该就大体上知道是啥意思，先上车🚗再说，很多东西都是用着用着就领悟了，编程更是如此，本篇文章目的主要是，让还没用上jdk8的，
         * 用了jdk8还对stream迟疑的童鞋早用早享受😃
         * 【🔗】唯有写出人类容易理解的代码，才是优秀的程序员！
         * 祝大家看得明白，玩得愉快！
         */

        /**
         * stream debug
         */

        /**
         * stream 上车技巧
         *      假如，刚开始不是很熟悉，可以先用常规for去实现，然后借助IDEA的智能，可以直接生成stream表达式，例如，需求一可以改写下:
         *      按照步骤操作后，看看会发生什么😋
         */
        /**需求一：竞猜最佳射手为梅西(bestPlayer为梅西)的用户有哪些*/
        List<String> names_guessed_Messi_old = new ArrayList<>();
        for (int i = 0; i < winnerGuesses.size(); i++) {
            if ("梅西".equals(winnerGuesses.get(i).getBestPlayer()))
                names_guessed_Messi_old.add(winnerGuesses.get(i).getName());
        }

        /**
         * 链接【🔗】
         */
    }

    /**
     * 给用户发送短信【这里用打印替代】
     *
     * @param winnerGuess
     */
    private static void sendMobileMsg(WinnerGuess winnerGuess) {
        System.out.println("Hi，" + winnerGuess.getName() + "，恭喜你竞猜冠军成功，100w奖金将会在你" + winnerGuess.getAge() +
                "岁生日\uD83C\uDF82，打到你的账户，到时请注意查收！");
    }


}
