package bizondemand.shopify-utils.model

import net.liftweb.mapper._
import net.liftweb.util._
import Helpers._
import net.liftweb.common._
import net.liftweb.common.{Empty, Box, Full}
import net.liftweb.sitemap.Loc.If
import _root_.net.liftweb.http.{S, LiftRules, RedirectResponse}
import S._
import scala_mash.shopify_api.model.{Shop, ShopCredentials}
import scala_mash.highrise_api.Account
import org.joda.time.DateTime
import xml.{Text,Elem}
import _root_.java.util.regex._
import bizondemand.utils.logging.Log


/**
 *
 * @author Jim Barrows
 *
 */

class ShopifyUser extends LongKeyedMapper[ShopifyUser] with IdPK {
  def getSingleton = ShopifyUser

  object timestamp extends MappedDateTime(this) 

  object shopName extends MappedString(this, 200) 

  object signature extends MappedString(this, 32)

  object authenticationToken extends MappedString(this, 32)

}

object ShopifyUser extends ShopInfo with LongKeyedMetaMapper[ShopifyUser] with CRUDify[Long, ShopifyUser] with Log {
  override def displayName = "Shopify User"

  override def createMenuLoc = Empty
  override def showAllMenuLoc = Empty
  override def viewMenuLoc = Empty
  override def deleteMenuLoc = Empty

  override def calcPrefix= List("shopifyUser")

}
