package com.privoce.youtube_sphere_backend.service;

import cn.authing.core.graphql.GraphQLException;
import cn.authing.core.mgmt.ManagementClient;
import cn.authing.core.types.JwtTokenStatus;
import cn.authing.core.types.PaginatedUsers;
import cn.authing.core.types.User;
import cn.authing.core.auth.AuthenticationClient;
import com.privoce.youtube_sphere_backend.Secret;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthingService {
    ManagementClient managementClient = new ManagementClient(Secret.USER_POOL_ID, Secret.USER_POOL_SECRET);
    AuthenticationClient authenticationClient = new AuthenticationClient(Secret.APP_ID, Secret.APP_HOST);

    public List<User> searchByKeyword(String keyword) throws IOException, GraphQLException {
        if (keyword != null) {
            PaginatedUsers result = managementClient.users().search(keyword).execute();
            return result.getList();
        } else
            return new ArrayList<>();
    }

    boolean checkLoginStatus(String token) {
        boolean ret = false;
        try {
            JwtTokenStatus status = authenticationClient.checkLoginStatus().execute();
            if (status != null)
                ret = status.getStatus();
        } catch (IOException | GraphQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public User getUserById(String userId) throws IOException {
        System.out.println(userId);
        return managementClient.users().detail(userId).execute();
    }
}
