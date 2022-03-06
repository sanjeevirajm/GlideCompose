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
