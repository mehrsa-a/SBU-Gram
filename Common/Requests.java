package Common;
/**
 * <h1>Requests</h1>
 * <p>this class is enum for requests that we send to server and receive a answer for them. its relation between server and client side</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public enum Requests {
    newUsername,
    login,
    signup,
    getPosts,
    getTimeline,
    getMyPosts,
    addPost,
    getUsers,
    follow,
    getNumbers,
    getNum,
    getFollowers,
    getFollowing,
    unfollow,
    like,
    dislike,
    getPostFeatures,
    getLikes,
    repost,
    getReposts,
    addComment,
    getComments,
    setProfile,
    changeProfile,
    getProfile,
    setInformation,
    getInformation,
    setForgetPassword,
    getForgetPassword,
    changePassword,
    deleteAccount,
    logout,
    block,
    unblock,
    getBlocker,
    getBlocked,
    mute,
    unMute,
    getMuted,
    sendMassage,
    receiveMassage,
    getMassages,
    readMassage,
    getMassaged,
    deleteMassage,
    editMassage
}
