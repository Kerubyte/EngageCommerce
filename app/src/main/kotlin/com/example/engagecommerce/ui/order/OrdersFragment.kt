package com.example.engagecommerce.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.adapter.OrderAdapter
import com.example.engagecommerce.databinding.FragmentOrdersBinding
import com.example.engagecommerce.repo.FirebaseCloud
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import kotlinx.android.synthetic.main.fragment_orders.*

@ScreenName(name = "Orders")
class OrdersFragment : RootFragment() {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var ordersViewModel: OrdersViewModel
    private val orderAdapter = OrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_orders,
            container,
            false)

        ordersViewModel = OrdersViewModel()
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewRecyclerOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ordersViewModel.orders.observe(viewLifecycleOwner, {
            orderAdapter.setOrders(it)
        })
    }


}