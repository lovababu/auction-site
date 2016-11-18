package com.sapient.auction.user.resource;

import com.sapient.auction.common.exception.SapAuctionException;
import com.sapient.auction.common.model.AuctionResponse;
import com.sapient.auction.common.model.UserVO;
import com.sapient.auction.user.entity.User;
import com.sapient.auction.user.exception.UserAlreadyExistException;
import com.sapient.auction.user.exception.UserNotFoundException;
import com.sapient.auction.user.service.UserService;
import com.sapient.auction.user.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User REST resource class.
 * Is responsible for create/update/delete/get User information.
 *
 * Created by dpadal on 11/10/2016.
 */

@Path(value = "/user")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * Register new User.
     *
     * @return Response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserVO userVO) throws SapAuctionException{
        User userEntity = ObjectMapperUtil.userEntity(userVO);
        log.info("Registration processing for the user: {}", userEntity.getId());
        try {
            userService.register(userEntity);
        } catch (UserAlreadyExistException e) {
           throw new SapAuctionException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "We are unable to process your request, please try again.");
        }
        return Response.status(Response.Status.CREATED).entity(
                AuctionResponse.builder()
                        .withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage("User registration successful.").build()
        ).build();
    }

    /**
     * Authenticate and Authorize user against the system.
     *
     * @return Response.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserVO userVO) throws SapAuctionException {
		User userEntity = null;
        try {
            userEntity = userService.login(ObjectMapperUtil.userEntity(userVO));
        } catch (UserNotFoundException e) {
            throw new SapAuctionException(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new SapAuctionException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "We are unable to process your request, please try again.");
        }
        return Response.status(Response.Status.OK).entity(
                AuctionResponse.builder()
                        .withStatusCode(Response.Status.OK.getStatusCode())
                        .withMessage("Logged in successful.").build()
        ).build();
    }

}
