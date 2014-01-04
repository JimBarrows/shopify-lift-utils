package bizondemand.shopify-utils

trait ShopifySupport {

	def packages = {

		LiftRules.addToPackages("bizondemand.shopify-utils")

	}

	def schemifierClasses = ShopifyUser :: Nil

	def sitemapEntries=Menu.i("View Shop") / "shopify" / "view" ::
			Menu.i("Edit Shop") / "shopify" / "edit" :: 
			Menu.i("Edit Shop") / "shopify" / "delete" :: 
			Nil

	
  def dispatchRules = {
    LiftRules.statelessRewrite.append {
			case RewriteRequest( ParsePath( List( "shopify" :: "install" :: Nil, _, _) => install _
		}
	}

	def statelessRewrites = {
		case RewriteRequest( ParsePath( List("shopify", "edit", shopName), _, _, _), _, _) => 
				RewriteResponse("shopify" :: "edit" :: Nil, Map("shopname" -> shopName))
	}

  def install : Box[ net.liftweb.http.LiftResponse] = {
    
    val shop = ShopifyUser.createInstance

    shop.timestamp.set( new Date())

    param( "shop")match {
      case Full(x) => shop.
  }
}
