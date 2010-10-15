package com.nsfw.customer_elevator.snippet

import scala.collection.jcl.Conversions._
import net.liftweb._
import common.{Failure, Empty, Full, Box}
import http._
import SHtml._
import S._

import util._
import Helpers._

import java.util.Date
import xml.{NodeSeq}
import org.joda.time.DateTime

import com.nsfw.customer_elevator.model.{ShopInfo, User}
import com.nsfw.customer_elevator.lib.CustomerElevatorConfig
import com.nsfw.customer_elevator.snippet.ShopInfoSnippets._
import bizondemand.utils.logging.Log
import scala_mash.shopify_api.model.{Shop,ShopCredentials}
import scala_mash.highrise_api.models.{Person, ContactData,EmailAddress,AddressLocationValues}
import scala_mash.highrise_api.models.enumerations.VisibleToValues

/**This is the interface that shopify uses to talk to us.
 *
 * @author jimbarrows
 * @created: Dec 10, 2009 11:39:13 PM
 * @version 1.0
 *
 */

class ShopifyInterface {

}

object ShopifyInterface extends Log {


  def addStore(): Box[net.liftweb.http.LiftResponse] = {
		if( currentShop.isDefined) return Full(RedirectResponse("/shopInfo/edit/" + currentShop.open_!.shopName))

    val shop = ShopInfo.createInstance

		shop.timestamp.set(new Date())

    param("shop") match {
      case Full(x) => shop.shopName.set(List.fromString(x, '.').first)
      case _ => error("shop", "Shopify did not send a shop url.")
    }

    param("signature") match {
      case Full(x) => shop.signature.set(x)
      case _ => error("signature", "Shopify did not send a signature.")
    }

    param("t") match {
      case Full(x) => {
        shop.authenticationToken.setFromAny(x)
      }
      case _ => error("t", "Shopify did not send an authentication token (the 't' param)")
    }

		info("Adding shop - {} Signature: {} Authentication Token {}", shop.shopName.get, shop.signature.get, 
					shop.authenticationToken.get)

		val shopifyShop = Shop.findShop(ShopCredentials(shop.shopName.get, shop.signature.get, 
					shop.authenticationToken.get))

		shop.save

		Person.create( Person (
			None, 
			shopifyShop.shopOwner.split(" ")(0),
			shopifyShop.shopOwner.split(" ")(1),
			"",
			"",
			None,
			None,
			None,
			Full(VisibleToValues.Everyone),
			None,
			None,
			None,
			ContactData(
				Full(EmailAddress(None, shopifyShop.email, AddressLocationValues.Other) :: Nil),
				None,
				None,
				None,
				None)), CustomerElevatorConfig.highriseAccount)
		ShopInfoSnippets.currentShop.set(Full( shop))
		Full(RedirectResponse("/shopInfo/edit/" + shop.shopName))

  }
}
