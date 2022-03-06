# GlideCompose
Glide Composable version

Demo: 
val context = LocalContext.current
GlideImage(
  modifier = Modifier.fillMaxSize(),
  contentDescription = "",
  data = item.imageUrl,
  placeHolderDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_crane_logo)!!,
  glideModifier = { requestBuilder ->
    requestBuilder.centerCrop()
   }
)

It does these two things,
* Properly cancels the image request
* Gets the target size using BoxWithConstraints and loads image only for the target size
