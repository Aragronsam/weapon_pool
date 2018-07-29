package com.aragron.weapon_pool.streams_api.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 世界杯竞猜
 *
 * @Author: aragron
 * @Create: 2018/7/25-13:39
 * @Home: http://aragron.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinnerGuess {

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 城市(来自哪里)
     */
    private String city;

    /**
     * 竞猜冠军球队
     */
    private String winnerTeam;

    /**
     * 最佳射手
     */
    private String bestPlayer;
}
