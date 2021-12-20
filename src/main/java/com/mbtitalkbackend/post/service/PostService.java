package com.mbtitalkbackend.post.service;

import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;

public interface PostService {

    PostEntity findPostEntityById(long post_id);

    Integer createPost(PostVO postVO);

    Integer patchPostById(long post_id, PostVO postVO);

    Integer deletePostById(long post_id);

}
