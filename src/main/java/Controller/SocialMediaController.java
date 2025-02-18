package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final AccountService accountService = new AccountService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) {
        Account acc = context.bodyAsClass(Account.class);
        Account registeredAccount = accountService.register(acc);

        if (registeredAccount == null) {
            context.status(400);
        } else {
            context.status(200).json(registeredAccount);
        }
        return;
    }

    private void loginHandler(Context context) {
        Account acc = context.bodyAsClass(Account.class);
        Account existsAccount = accountService.login(acc);

        if (existsAccount == null) {
            context.status(401);
        }
        else {
            context.status(200).json(existsAccount);
        }
        return;
    }



}