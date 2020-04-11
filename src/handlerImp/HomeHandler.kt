package handlerImp

import handler.HttpHandler
import handler.Request
import handler.Response

class HomeHandler: HttpHandler() {
    override fun onGet(request: Request, response: Response) {
        response.body="""
            <!DOCTYPE html>
            <head>
                <title>Home</title>
                <link rel="shortcut icon" type="image/x-icon" href="./assets/favicon.ico"/>
                <meta charset="unicode"/>
                <meta name="viewport" content="width=device-width, initial-scale=1"/>
            </head>

            <body>
             <h1>Địt pẹ mày</h1>
            </body>
            </html>
        """.trimIndent()
    }

    override fun onPost(request: Request, response: Response) {
        TODO("Not yet implemented")
    }
}