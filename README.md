An example of versioned assets.

If you want to use this in your project, copy the VersionedAssets controller into your project, add the following route
to your project:

    GET     /assets/*file               controllers.VersionedAssets.at(file: controllers.VersionedAsset)

And add the following import statement to your templates:

    @import controllers.VersionedAssets._

And then just use it like you use the normal assets reverse router, eg:

    <link rel="stylesheet" media="screen" href="@routes.VersionedAssets.at("stylesheets/main.css")">
