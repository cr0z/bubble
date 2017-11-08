package com.crozsama.bubble.network;

import java.util.List;

/**
 * Created by 93201 on 2017/11/8.
 */

public class DynamicResponse {
    public int code;
    public List<Bubble> data;

    public class Bubble {
        public int id;
        public String username;
        public String name;
        public String content;
        public String media;
        public int visible;
        public int created_at;
        public int comment_count;
        public int liked_count;
    }
}
